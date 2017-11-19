import breeze.linalg.DenseVector
import neuroflow.application.plugin.IO
import neuroflow.core.WeightProvider.FFN


object Training {

  val t = Seq(
    Seq("b", "c", "x", "f", "k", "s"),
    Seq("f", "g", "y", "s"),
    Seq("n", "b", "c", "g", "r", "p", "u", "e", "w", "y"),
    Seq("t", "f"),
    Seq("a", "l", "c", "y", "f", "m", "n", "p", "s"),
    Seq("a", "d", "f", "n"),
    Seq("c", "w", "d"),
    Seq("b", "n"),
    Seq("k", "n", "b", "h", "g", "r", "o", "p", "u", "e", "w", "y"),
    Seq("e", "t"),
    Seq("b", "c", "u", "e", "z", "r", "?"),
    Seq("f", "y", "k", "s"),
    Seq("f", "y", "k", "s"),
    Seq("n", "b", "c", "g", "o", "p", "e", "w", "y"),
    Seq("n", "b", "c", "g", "o", "p", "e", "w", "y"),
    Seq("p", "u"),
    Seq("n", "o", "w", "y"),
    Seq("n", "o", "t"),
    Seq("c", "e", "f", "l", "n", "p", "s", "z"),
    Seq("k", "n", "b", "h", "r", "o", "u", "w", "y"),
    Seq("a", "c", "n", "s", "v", "y"),
    Seq("g", "l", "m", "p", "u", "w", "d")
  )

  def main(args: Array[String]): Unit = {
    implicit val randomWeightProvider = FFN[Double].random(-1, 1)
    val net = NetworkFactory.createNetwork(randomWeightProvider)

    val (input: Seq[Seq[Double]], output: Seq[Seq[Double]]) = Util.read("training.data")
    val in: Seq[DenseVector[Double]] = input.map(seq => new DenseVector[Double](seq.toArray))
    val out: Seq[DenseVector[Double]] = output.map(seq => new DenseVector[Double](seq.toArray))
    net.train(in, out)

    IO.File.write(net.weights, "build/mushrooms.json")
  }
}
