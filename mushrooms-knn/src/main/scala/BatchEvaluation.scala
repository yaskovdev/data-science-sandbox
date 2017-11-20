import scala.io.{Source, StdIn}

object BatchEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = StdIn.readLine()
    val featureVector: Seq[String] = string.split(",").toSeq
    val data = read("training.data")
    println(closest(featureVector.tail, data).head)
  }

  private def read(resource: String): Seq[Seq[String]] = {
    Source.fromResource(resource).getLines().map(_.split(",").toSeq).toSeq
  }

  private def closest(y: Seq[String], xs: Seq[Seq[String]]): Seq[String] = {
    var candidate: Seq[String] = Seq()
    var currentDistance: Double = Double.MaxValue
    for (x <- xs) {
      val newDistance = distance(x.tail, y)
      if (newDistance < currentDistance) {
        currentDistance = newDistance
        candidate = x
      }
    }
    candidate
  }

  private def distance(x: Seq[String], y: Seq[String]): Double = {
    x.zip(y).foldLeft(0.0)((sum, pair) => if (pair._1 == pair._2) sum else sum + 1.0)
  }
}
