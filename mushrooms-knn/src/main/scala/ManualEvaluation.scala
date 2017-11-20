import scala.io.StdIn

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = StdIn.readLine()
    val featureVector: Seq[String] = string.split(",").toSeq
    val data = new ResourceData().read("training.data")
    println(new NearestNeighbor().nearest(featureVector.tail, data, 3))
  }
}
