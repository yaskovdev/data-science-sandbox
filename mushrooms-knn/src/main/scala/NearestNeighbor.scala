class NearestNeighbor {

  def nearest(y: Seq[String], xs: Seq[Seq[String]], k: Int): String = {
    val knn = xs.sortWith((a, b) => distance(a.tail, y) < distance(b.tail, y)).slice(0, k)
    val edibleNumber: Int = knn.foldLeft(0)((count, element) => if (element.head == "e") count + 1 else count)
    if (edibleNumber > k / 2) "e" else "p"
  }

  //
  // Auxiliary Methods
  //

  private def distance(x: Seq[String], y: Seq[String]): Double = {
    require(x.lengthCompare(y.size) == 0, "mismatch between vector sizes")
    x.zip(y).foldLeft(0.0)((sum, pair) => if (pair._1 == pair._2) sum else sum + 1.0)
  }
}
