package analytics.chapters

import java.lang.{Double => JDouble}

import org.apache.spark.rdd.RDD
import org.apache.spark.util.StatCounter

object Chapter2 extends Chapter {

  case class MatchData(id1: Int, id2: Int, scores: Array[Double], matched: Boolean) {

    def scoreOrZero(feature: Int): Double =
      extendedRealOrZero(scores(feature))

    private def extendedRealOrZero(d: Double): Double =
      if (JDouble.isNaN(d)) 0.0 else d

  }

  case class Scored(data: MatchData, score: Double)

  class NAStatCounter extends Serializable {

    private val stats = new StatCounter()
    private var _missing: Long = 0

    def add(d: Double): NAStatCounter = {
      if (JDouble.isNaN(d)) _missing += 1
      else stats.merge(d)
      this
    }

    def merge(other: NAStatCounter): NAStatCounter = { stats.merge(other.stats)
      _missing += other._missing
      this
    }

    def missing: Long = _missing

    def mean: Double = stats.mean

    override def toString: String =
      s"stats: ${stats.toString} NaN: $missing"

  }

  object NAStatCounter extends Serializable {
    def apply(d: Double): NAStatCounter = new NAStatCounter().add(d)
  }

  def toDouble(s: String) =
    if (s == "?") Double.NaN else s.toDouble

  def nonHeader(line: String): Boolean =
    !line.contains("id_1")

  def toData(line: String): MatchData = {
    val pieces = line.split(",")
    val id1 = pieces(0).toInt
    val id2 = pieces(1).toInt
    val scores = pieces.slice(2, 11).map(toDouble)
    val matched = pieces(11).toBoolean
    MatchData(id1, id2, scores, matched)
  }

  def scoreCounters(data: MatchData): Array[NAStatCounter] =
    data.scores.map(NAStatCounter.apply)

  def mergeCounters(d0: Array[NAStatCounter], d1: Array[NAStatCounter]): Array[NAStatCounter] =
    d0.zip(d1).map { case (c0, c1) => c0.merge(c1) }


  implicit class NaNStats(rdd: RDD[MatchData]) extends Serializable {

    def matchedStats: Array[NAStatCounter] = stats(_.matched)

    def notMatchedStats: Array[NAStatCounter] = stats(!_.matched)

    private def stats(filter: MatchData => Boolean): Array[NAStatCounter] = {
      rdd
        .filter(filter)
        .map(scoreCounters)
        .treeReduce(mergeCounters)
    }

  }


  val rawBlocks = sc.textFile(data("linkage").getAbsolutePath)

  val parsedData = rawBlocks
    .filter(nonHeader)
    .map(toData)

  val matchedStats = parsedData.matchedStats
  val notMatchedStats = parsedData.notMatchedStats

  val combinedStats = matchedStats.zip(notMatchedStats).map { case (m, n) =>
    (m.missing + n.missing, m.mean - n.mean)
  }

  val once = (d: Double) => d
  val twice = (d: Double) => 2 * d
  val selectedFeatures: Seq[(Int, Double => Double)] = Seq(
    2 -> once,
    5 -> twice,
    6 -> once,
    7 -> twice,
    8 -> once
  )
  val scoredData = parsedData.map { data =>
    val score = selectedFeatures.foldLeft(0d) { case (sum, (feature, f)) =>
      sum + f(data.scoreOrZero(feature))
    }
    Scored(data, score)
  }

  val featureMinimum = 0.9
  val threshold = selectedFeatures.foldLeft(0d) { case (sum, (_, f)) => sum + f(featureMinimum) }
  val modelFilteredData = scoredData
    .filter(_.score >= threshold)
    .map(_.data.matched)
    .countByValue()

  val totalCount = scoredData.count()

  combinedStats.zipWithIndex.foreach { case ((missing, meanDifference), feature) =>
    println(f"$feature: $missing%-10d $meanDifference%.4f")
  }

  println(s"""
             |#data: $totalCount
             |#filtered matched: ${modelFilteredData(true)}
             |#filtered non matched: ${modelFilteredData(false)}
    """.stripMargin)

}
