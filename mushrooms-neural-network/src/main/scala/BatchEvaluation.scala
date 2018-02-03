import breeze.linalg.DenseVector
import neuroflow.application.plugin.IO
import neuroflow.core.{Network, WeightProvider}

object BatchEvaluation {

  def main(args: Array[String]): Unit = {
    implicit val weightProvider: WeightProvider[Double] = IO.File.readDouble("build/mushrooms.json")
    val net = NetworkFactory.createNetwork(weightProvider)
    val (input: Seq[Seq[Double]], output: Seq[Seq[Double]]) = Util.read("evaluation.data")
    val in: Seq[DenseVector[Double]] = input.map(seq => new DenseVector[Double](seq.toArray))
    val out: Seq[DenseVector[Double]] = output.map(seq => new DenseVector[Double](seq.toArray))

    var total = 0
    var correct = 0
    for ((vector, index) <- in.zipWithIndex) {
      total += 1
      val expected = out(index)
      val actual: Network.Vector[Double] = net(vector)
      if (Util.interpretResult(expected) == Util.interpretResult(actual)) {
        correct += 1
      }
    }
    val rate = correct.asInstanceOf[Double] / total
    println("Rate of correct guesses is " + rate)
  }
}
