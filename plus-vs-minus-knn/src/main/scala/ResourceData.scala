import scala.io.Source

class ResourceData {

  def read(resource: String): Seq[Seq[String]] = {
    Source.fromResource(resource).getLines().map(_.split(",").toSeq).toSeq
  }
}
