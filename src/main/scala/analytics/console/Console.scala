package analytics.console

import analytics.spark.{Data, Context}

object Console extends Context with Data {

  //initialCommands in console := """
  //  |ammonite.repl.Main.run(analytics.console.Console._initialCommands)
  //  |""".stripMargin

  lazy val _initialCommands = s"""
      |import analytics.console.Console._
      |import org.apache.spark.{repl => sparkRepl, _}
      |import org.apache.spark.rdd._
      |import org.apache.spark.util._
      |import analytics.chapters._
    """.stripMargin

}
