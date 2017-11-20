import scala.io.{Source, StdIn}

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = StdIn.readLine()
    val featureVector: Seq[String] = string.split(",").toSeq
    val data = read("training.data")
    println(nearest(featureVector.tail, data).head)
  }

  private def read(resource: String): Seq[Seq[String]] = {
    Source.fromResource(resource).getLines().map(_.split(",").toSeq).toSeq
  }

  private def nearest(y: Seq[String], xs: Seq[Seq[String]]): Seq[String] = {
    xs.sortWith((a, b) => distance(a.tail, y) < distance(b.tail, y)).head
  }

  private def distance(x: Seq[String], y: Seq[String]): Double = {
    require(x.size == y.size, "mismatch between vector sizes")
    x.zip(y).foldLeft(0.0)((sum, pair) => if (pair._1 == pair._2) sum else sum + 1.0)
  }
}
