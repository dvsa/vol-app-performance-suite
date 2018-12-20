package utils

import scala.annotation.switch

object Configuration {
  val environment: String = System.getProperty("env")
  val users: Int = System.getProperty("users").toInt
  val rampUp: Int = System.getProperty("rampUp").toInt
  val rampDurationInMin: Int = System.getProperty("duration").toInt
  val baseURL = (environment: @switch) match {
    case "int" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.prod.dvsa.aws/")
    case _ => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.nonprod.dvsa.aws/")
  }
  val securityTokenPattern = """id="security" value="([^"]*)&*"""
  val header_ = Map("Accept" -> "*/*")
}