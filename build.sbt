name := "analytics-with-spark"

version := "0.0.2"

scalaVersion := "2.11.7"

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-repl" % "1.6.0",
  "com.lihaoyi" % "ammonite-repl" % "0.5.4" cross CrossVersion.full
)

initialCommands in console :=
  """
    |import analytics.console.Console._
    |import org.apache.spark._
    |import org.apache.spark.rdd._
    |import org.apache.spark.util._
    |import analytics.chapters._
  """.stripMargin
