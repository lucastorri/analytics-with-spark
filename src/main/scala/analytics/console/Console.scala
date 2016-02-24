package analytics.console

import analytics.spark.Context

object Console extends Context {

  val _initialCommands = """
      |import analytics.console.Console._
      |import org.apache.spark.{repl => sparkRepl, _}
      |import org.apache.spark.rdd._
      |import org.apache.spark.util._
      |import analytics.chapters._
    """.stripMargin

}
