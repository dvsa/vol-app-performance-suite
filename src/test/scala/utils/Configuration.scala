package utils

import scala.annotation.switch

object Configuration {
  val environment: String = System.getProperty("env")


  val users: Int = 0
  val externalUsers: Int = 0
  val internalUsers: Int = 7
  val rampUp: Int = System.getProperty("rampUp").toInt
  val rampDurationInMin: Int = System.getProperty("duration").toInt
  val baseURL = (environment.toLowerCase: @switch) match {
    case "int" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.prod.dvsa.aws/")
    case "intpp" => scala.util.Properties.envOrElse("baseURL", s"https://iuap1.olcs.int.prod.dvsa.aws/")
    case _ => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.nonprod.dvsa.aws/")
  }
  val securityTokenPattern = """id="security" value="([^"]*)&*"""
  val header_ = Map("Accept" -> "*/*")
}