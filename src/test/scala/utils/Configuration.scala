package utils

import scala.annotation.switch

object Configuration {

  val users: Int = Option(System.getProperty("users").toInt).getOrElse(10)
  val environment: String = Option(System.getProperty("env").toLowerCase).getOrElse("qa")
  val externalUsers: Int = Option(700).getOrElse(System.getProperty("externalUsers").toInt)
  val internalUsers: Int = Option(30).getOrElse(System.getProperty("internalUsers").toInt)
  val rampUp: Int = Option(System.getProperty("rampUp").toInt).getOrElse(0)
  val rampDurationInMin: Int = Option(System.getProperty("duration").toInt).getOrElse(0)

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
  val cpmsRedirectURL = """action="https://sbsctest.e-paycapita.com:443/scp/scpcli?(.*)""""
}