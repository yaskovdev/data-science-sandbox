import neuroflow.application.plugin.Notation._
import neuroflow.core.Activator._
import neuroflow.core._
import neuroflow.nets.cpu.DenseNetwork._
import shapeless._


object Main {

  def main(args: Array[String]): Unit = {
    implicit val wp = neuroflow.core.WeightProvider.FFN[Double].random(-1, 1)
    val f = Sigmoid
    val settings = Settings[Double](
      learningRate = {
        case (_, _) => 1.0
      },
      iterations = 10000,
      lossFuncOutput = None)
    val net = Network(Input(2) :: Dense(3, f) :: Output(1, f) :: HNil, settings)

    val xs = Seq(->(0.0, 0.0), ->(0.0, 1.0), ->(1.0, 0.0), ->(1.0, 1.0))
    val ys = Seq(->(0.0), ->(1.0), ->(1.0), ->(0.0))

    net.train(xs, ys)

    val x = ->(1.0, 0.0)
    val result = net(x)
    println(result)
  }
}
