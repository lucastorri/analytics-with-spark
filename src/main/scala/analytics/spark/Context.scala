package analytics.spark

import org.apache.spark.{SparkConf, SparkContext}

trait Context {

  def sc = Context.runningContext

}

object Context {

  val sparkConf =
    new SparkConf()
      .setAppName(s"analytics-with-spark")
      .setMaster("local[*]")

  lazy val runningContext = {
    val sc = new SparkContext(sparkConf)
    sys.addShutdownHook(sc.stop())
    sc
  }

}
