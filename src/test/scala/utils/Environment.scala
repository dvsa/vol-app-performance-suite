package utils

object Environment{
  val environment = System.getProperty("env")
  val baseURL = scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.nonprod.dvsa.aws/")
}