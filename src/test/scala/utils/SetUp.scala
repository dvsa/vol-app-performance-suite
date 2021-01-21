package utils

import org.dvsa.testing.lib.url.webapp.URL
import org.dvsa.testing.lib.url.webapp.utils.ApplicationType

import scala.annotation.switch

object SetUp {

  val site: String = System.getProperty("site")
  val env: String = System.getProperty("env").toLowerCase
  val users: Int = System.getProperty("users").toInt
  val rampUp: Int = System.getProperty("rampUp").toInt
  val rampDurationInMin: Int = System.getProperty("duration").toInt
  val externalURL: String = URL.build(ApplicationType.EXTERNAL, env).toString
  val internalURL: String = URL.build(ApplicationType.INTERNAL, env).toString

  println("RAMPUP" + rampUp + rampDurationInMin )
  val baseURL: String =
    (site: @switch) match {
      case "ss" => scala.util.Properties.envOrElse("baseURL", externalURL)
      case "internal" => scala.util.Properties.envOrElse("baseURL", internalURL)
    }
  val securityTokenPattern = """id="security" value="([^"]*)&*"""
  val header_ = Map("Accept" -> "*/*")
  val location = """name="change-password-form" action="&#x2F;auth&#x2F;expired-password&#x2F;([^"]*)&#x2F;"""
  val cpmsRedirectURL = """action="https://sbsctest.e-paycapita.com:443/scp/scpcli?(.*)""""
}