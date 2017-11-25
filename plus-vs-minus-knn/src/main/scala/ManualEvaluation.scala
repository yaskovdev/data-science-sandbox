import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.io.StdIn

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a file name:")
    val fileName = StdIn.readLine()
    val input: Seq[Int] = readImage(fileName)
    val minusesFiles = new File("C:\\dev\\git_home\\data-science-sandbox\\plus-vs-minus-knn\\src\\main\\resources\\minuses").listFiles()
    val minuses = minusesFiles.map(file => Sign("minus", readImage(file.getPath))).toSeq
    val plusesFiles = new File("C:\\dev\\git_home\\data-science-sandbox\\plus-vs-minus-knn\\src\\main\\resources\\pluses").listFiles()
    val pluses = plusesFiles.map(file => Sign("plus", readImage(file.getPath))).toSeq
    val data = minuses ++ pluses

    val result = new NearestNeighbor().nearest(Sign("unknown", input), data, 3)
    println(result.name)
  }

  private def readImage(fileName: String): Seq[Int] = {
    val image: BufferedImage = ImageIO.read(new File(fileName))
    val width = image.getWidth
    val height = image.getHeight
    val array: Array[Int] = new Array[Int](width * height)
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val value = image.getRGB(x, y)
        array(height * x + y) = if (value == -1) 0 else 1
      }
    }
    array.toSeq
  }
}
