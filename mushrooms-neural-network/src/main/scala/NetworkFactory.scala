import neuroflow.core.Activator._
import neuroflow.core._
import neuroflow.nets.cpu.DenseNetwork._
import shapeless._

object NetworkFactory {

  def createNetwork(implicit weightProvider: WeightProvider[Double]): FFN[Double] = {
    val f = Sigmoid
    val settings = Settings[Double](
      iterations = 900,
      lossFuncOutput = None)
    Network(Input(126) :: Dense(10, f) :: Output(2, f) :: HNil, settings)
  }
}
