package analytics.chapters

import java.io.File

import analytics.spark.{Context, Data}

trait Chapter extends Context with Data with App {

  final def run(): Unit =
    main(Array.empty)

  def chapter: Int =
    getClass.getSimpleName.replaceAll("(Chapter|\\$)", "").toInt

  def data(path: String): File =
    data(chapter, path)

}
