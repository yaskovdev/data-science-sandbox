import Training.t

object Util {

  def featureVectorAsInputOutputPair(featureVector: Seq[String]): (Seq[Double], Seq[Double]) = {
    val input = featureVector.tail.zipWithIndex.flatMap { case (feature, index) => featureAsInput(feature, t(index)) }
    val output = if (featureVector.head == "e") Seq(1.0, 0.0) else Seq(0.0, 1.0)
    (input, output)
  }

  private def featureAsInput(feature: String, possibleValues: Seq[String]): Seq[Double] = {
    possibleValues.map(v => if (v == feature) 1.0 else 0.0)
  }
}
