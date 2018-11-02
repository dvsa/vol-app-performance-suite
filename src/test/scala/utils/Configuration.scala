package utils

object Configuration {
  val environment: String = System.getProperty("env")
  val users: Int = System.getProperty("users").toInt
  val rampUp: String = System.getProperty("rampup")
  val testDuration: String = System.getProperty("duration")
  val baseURL: String = scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.nonprod.dvsa.aws/")
}