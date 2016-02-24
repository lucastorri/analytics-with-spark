package analytics.spark

import org.apache.spark.util.SparkBuild
import org.apache.spark.{SparkConf, SparkContext}

trait Context {

  def sc = Context.runningContext

}

object Context {

  val sparkConf =
    new SparkConf()
      .setAppName(s"analytics-with-spark")
      .setMaster("local[*]")

  val outDir = SparkBuild.outdir(sparkConf)

  lazy val runningContext = {
    //sparkConf.set("spark.repl.class.outputDir", outDir.getAbsolutePath)
    val sc = new SparkContext(sparkConf)
    sys.addShutdownHook(sc.stop())
    sc
  }

}
