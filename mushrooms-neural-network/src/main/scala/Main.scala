import neuroflow.application.plugin.Notation._
import neuroflow.core.Activator._
import neuroflow.core._
import neuroflow.nets.cpu.DenseNetwork._
import shapeless._

import scala.io.Source


object Main {

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
    implicit val wp = neuroflow.core.WeightProvider.FFN[Double].random(-1, 1)
    val f = Sigmoid
    val settings = Settings[Double](
      learningRate = {
        case (_, _) => 1.0
      },
      verbose = false,
      prettyPrint = true,
      iterations = 10000,
      lossFuncOutput = None)
    val net = Network(Input(126) :: Dense(127, f) :: Output(2, f) :: HNil, settings)

    val xs = Seq(->(0.0, 0.0), ->(0.0, 1.0), ->(1.0, 0.0), ->(1.0, 1.0))
    val ys = Seq(->(0.0), ->(1.0), ->(1.0), ->(0.0))

    net.train(xs, ys)

    val x = ->(1.0, 0.0)
    val result = net(x)
    println(result)
  }

  private def read(): Seq[Seq[Double]] = {
    val iterator: Iterator[Seq[String]] = Source.fromResource("agaricus-lepiota.data").getLines().map(_.split(",").toSeq)
    iterator.map(l => asInput(l)).toSeq
  }

  private def asInput(featureVector: Seq[String]) = {
    featureVector.zipWithIndex.flatMap { case (feature, index) => v(feature, t(index)) }
  }

  private def v(value: String, values: Seq[String]): Seq[Double] = {
    values.map(v => if (v == value) 1.0 else 0.0)
  }
}
