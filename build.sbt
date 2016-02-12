name := "analytics-with-spark"

version := "0.0.1"

scalaVersion := "2.11.7"

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0",
  "com.lihaoyi" % "ammonite-repl" % "0.5.4" cross CrossVersion.full
)

initialCommands in console := """
  |ammonite.repl.Main.run(analytics.console.Console._initialCommands)
  |""".stripMargin
