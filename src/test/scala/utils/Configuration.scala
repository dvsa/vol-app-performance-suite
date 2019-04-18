package utils

import scala.annotation.switch

object Configuration {
  val environment: String = Option("qa").getOrElse(System.getProperty("env").toLowerCase)
  val users: Int = Option(10).getOrElse(System.getProperty("users").toInt)
  val externalUsers: Int = Option(300).getOrElse(System.getProperty("externalUsers").toInt)
  val internalUsers: Int = Option(30).getOrElse(System.getProperty("externalUsers").toInt)
  val rampUp: Int = Option(0).getOrElse(System.getProperty("rampUp").toInt)
  val rampDurationInMin: Int = Option(0).getOrElse(System.getProperty("duration").toInt)

  val baseURL =
    (environment: @switch) match {
      case "int" => scala.util.Properties.envOrElse("baseURL", s"https://iuap1.olcs.int.prod.dvsa.aws/")
      case "ints" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.int.prod.dvsa.aws/")
      case "iqa" => scala.util.Properties.envOrElse("baseURL", s"https://iuap1.olcs.qa.nonprod.dvsa.aws/")
      case "qa" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.qa.nonprod.dvsa.aws/")
  }
  val securityTokenPattern = """id="security" value="([^"]*)&*"""
  val header_ = Map("Accept" -> "*/*")
  val location = """name="change-password-form" action="&#x2F;auth&#x2F;expired-password&#x2F;([^"]*)&#x2F;"""
}