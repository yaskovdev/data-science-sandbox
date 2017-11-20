

object BatchEvaluation {

  def main(args: Array[String]): Unit = {
    val resourceData = new ResourceData
    val trainingData = resourceData.read("training.data")

    var current = 0
    var correct = 0
    val nearestNeighbor = new NearestNeighbor()
    val evaluationData = resourceData.read("evaluation.data")
    for (vector <- evaluationData) {
      val expected: String = vector.head
      val actual: String = nearestNeighbor.nearest(vector.tail, trainingData, 3)
      if (expected == actual) {
        correct += 1
      }
      current += 1
      println("Processed " + current + " of " + evaluationData.size + ", number of correct answers is " + correct)
    }
    val rate = correct.asInstanceOf[Double] / evaluationData.size
    println("Rate of correct guesses is " + rate)
  }
}
