import breeze.linalg.DenseVector
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
      iterations = 500,
      lossFuncOutput = None)
    val net = Network(Input(126) :: Dense(127, f) :: Output(2, f) :: HNil, settings)

    val (input: Seq[Seq[Double]], output: Seq[Seq[Double]]) = read()
    val in: Seq[DenseVector[Double]] = input.map(seq => new DenseVector[Double](seq.toArray))
    val out: Seq[DenseVector[Double]] = output.map(seq => new DenseVector[Double](seq.toArray))
    net.train(in, out)

    val x = ->(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
    val result = net(x)
    println(result)
  }

  private def read(): (Seq[Seq[Double]], Seq[Seq[Double]]) = {
    val iterator: Iterator[Seq[String]] = Source.fromResource("training.data").getLines().map(_.split(",").toSeq)
    val seq: Seq[(Seq[Double], Seq[Double])] = iterator.map(l => featureVectorAsInput(l)).toSeq
    seq.unzip
  }

  private def featureVectorAsInput(featureVector: Seq[String]): (Seq[Double], Seq[Double]) = {
    val input = featureVector.tail.zipWithIndex.flatMap { case (feature, index) => featureAsInput(feature, t(index)) }
    val output = if (featureVector.head == "e") Seq(1.0, 0.0) else Seq(0.0, 1.0)
    (input, output)
  }

  private def featureAsInput(feature: String, possibleValues: Seq[String]): Seq[Double] = {
    possibleValues.map(v => if (v == feature) 1.0 else 0.0)
  }
}
