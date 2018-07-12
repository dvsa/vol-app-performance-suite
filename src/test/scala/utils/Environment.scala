package utils

object Environment{
  val environment = System.getProperty("env")
  val user = System.getProperty("users")
  val interval = System.getProperty("interval")
  val baseURL = scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.nonprod.dvsa.aws/")
}