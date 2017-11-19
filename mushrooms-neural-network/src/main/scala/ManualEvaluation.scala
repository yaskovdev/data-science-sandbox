import breeze.linalg.DenseVector
import neuroflow.application.plugin.IO
import neuroflow.core.Network

object ManualEvaluation {

  def main(args: Array[String]): Unit = {
    println("Enter a mushroom description:")
    val string = scala.io.StdIn.readLine()
    implicit val weightProvider = IO.File.readDouble("build/mushrooms.json")
    val net = NetworkFactory.createNetwork(weightProvider)
    val featureVector: Seq[String] = string.split(",").toSeq
    val (input, _): (Seq[Double], Seq[Double]) = Util.featureVectorAsInputOutputPair(featureVector)
    val result: Network.Vector[Double] = net(new DenseVector[Double](input.toArray))
    val (edible: Double, poisonous: Double) = (result.toScalaVector().head, result.toScalaVector().tail.head)
    println((if (edible > poisonous) "edible" else "poisonous") + " (" + result + ")")
  }
}
