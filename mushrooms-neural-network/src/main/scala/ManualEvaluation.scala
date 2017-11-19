import breeze.linalg.DenseVector
import neuroflow.application.plugin.IO
import neuroflow.core.Network

import scala.io.StdIn

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = StdIn.readLine()

    val featureVector: Seq[String] = string.split(",").toSeq
    val (input, _): (Seq[Double], Seq[Double]) = Util.featureVectorAsInputOutputPair(featureVector)

    implicit val weightProvider = IO.File.readDouble("build/mushrooms.json")
    val net = NetworkFactory.createNetwork(weightProvider)
    val result: Network.Vector[Double] = net(new DenseVector[Double](input.toArray))
    println(Util.interpretResult(result) + " (" + result + ")")
  }
}
