

object BatchEvaluation {

  def main(args: Array[String]): Unit = {
    val resourceData = new ResourceData
    val trainingData = resourceData.read("training.data")

    var current = 0
    var correct = 0
    val nearestNeighbor = new NearestNeighbor()
    val evaluationData = resourceData.read("evaluation.data")
    val size = evaluationData.size
    for (vector <- evaluationData) {
      val expected: String = vector.head
      val actual: String = nearestNeighbor.nearest(vector.tail, trainingData, 3)
      if (expected == actual) {
        correct += 1
      }
      current += 1
      println(s"Processed $current of $size, number of errors is ${current - correct}")
    }
    val rate = correct.asInstanceOf[Double] / size
    println(s"Rate of correct answers is $rate")
  }
}
