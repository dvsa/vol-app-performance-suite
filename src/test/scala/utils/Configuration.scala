package utils

import scala.annotation.switch

object Configuration {
  val environment: String = System.getProperty("env")
  val users: Int = 20
  val externalUsers: Int = 900
  val internalUsers: Int = 30
  val rampUp: Int = 0
  val rampDurationInMin: Int =0

  val baseURL =
    (environment.toLowerCase: @switch) match {
      case "int" => scala.util.Properties.envOrElse("baseURL", s"https://iuap1.olcs.int.prod.dvsa.aws/")
      case "ints" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.int.prod.dvsa.aws/")
      case "iqa" => scala.util.Properties.envOrElse("baseURL", s"https://iuap1.olcs.qa.nonprod.dvsa.aws/")
      case "qa" => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.qa.nonprod.dvsa.aws/")
      case _ => scala.util.Properties.envOrElse("baseURL", s"https://ssap1.olcs.$environment.prod.dvsa.aws/")
  }

  val securityTokenPattern = """id="security" value="([^"]*)&*"""
  val header_ = Map("Accept" -> "*/*")
  val location = """name="change-password-form" action="&#x2F;auth&#x2F;expired-password&#x2F;([^"]*)&#x2F;"""
}