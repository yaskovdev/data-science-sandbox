class NearestNeighbor {

  def nearest(unknown: Sign, xs: Seq[Sign], k: Int): Sign = {
    val knn = xs.sortWith((a, b) => distance(a.image, unknown.image) < distance(b.image, unknown.image)).slice(0, k)
    val plusesNumber: Int = knn.foldLeft(0)((count, element) => if (element.name == "plus") count + 1 else count)
    if (plusesNumber > k / 2) Sign("plus", unknown.image) else Sign("minus", unknown.image)
  }

  //
  // Auxiliary Methods
  //

  private def distance(x: Seq[Int], y: Seq[Int]): Double = {
    require(x.size == y.size, "mismatch between vector sizes")
    x.zip(y).foldLeft(0.0)((sum, pair) => if (pair._1 == pair._2) sum else sum + 1.0)
  }
}
