package analytics.spark

import java.io.File

trait Data {

  def userDir: String =
    System.getProperty("user.dir")

  def dataDir: String =
    s"$userDir/data"

  def data(chapter: Int, path: String): File =
    new File(s"$dataDir/chapter-$chapter/$path")

}
