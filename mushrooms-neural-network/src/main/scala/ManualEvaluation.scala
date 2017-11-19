import breeze.linalg.DenseVector
import neuroflow.application.plugin.IO
import neuroflow.core.Network

import scala.io.StdIn

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = StdIn.readLine()
    implicit val weightProvider = IO.File.readDouble("build/mushrooms.json")
    val net = NetworkFactory.createNetwork(weightProvider)
    val featureVector: Seq[String] = string.split(",").toSeq
    val (input, _): (Seq[Double], Seq[Double]) = Util.featureVectorAsInputOutputPair(featureVector)
    val result: Network.Vector[Double] = net(new DenseVector[Double](input.toArray))
    println(Util.interpretResult(result) + " (" + result + ")")
  }
}
