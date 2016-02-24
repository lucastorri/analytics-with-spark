package org.apache.spark.util

import java.io.File

import org.apache.spark.SparkConf

object SparkBuild {

  def outdir(conf: SparkConf): File = {
    val rootDir = conf.getOption("spark.repl.classdir").getOrElse(Utils.getLocalDir(conf))
    val outputDir = Utils.createTempDir(root = rootDir, namePrefix = "repl")
    outputDir
  }

}
