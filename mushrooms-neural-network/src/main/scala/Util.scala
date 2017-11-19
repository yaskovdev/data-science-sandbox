import Training.t
import neuroflow.core.Network

import scala.io.Source

object Util {

  def read(resource: String): (Seq[Seq[Double]], Seq[Seq[Double]]) = {
    val iterator: Iterator[Seq[String]] = Source.fromResource(resource).getLines().map(_.split(",").toSeq)
    val seq: Seq[(Seq[Double], Seq[Double])] = iterator.map(l => Util.featureVectorAsInputOutputPair(l)).toSeq
    seq.unzip
  }

  def featureVectorAsInputOutputPair(featureVector: Seq[String]): (Seq[Double], Seq[Double]) = {
    val input = featureVector.tail.zipWithIndex.flatMap { case (feature, index) => featureAsInput(feature, t(index)) }
    val output = if (featureVector.head == "e") Seq(1.0, 0.0) else Seq(0.0, 1.0)
    (input, output)
  }

  def interpretResult(result: Network.Vector[Double]): String = {
    val (edible: Double, poisonous: Double) = (result.toScalaVector().head, result.toScalaVector().tail.head)
    if (edible > poisonous) "edible" else "poisonous"
  }

  private def featureAsInput(feature: String, possibleValues: Seq[String]): Seq[Double] = {
    possibleValues.map(v => if (v == feature) 1.0 else 0.0)
  }
}
