package utils

import activesupport.aws.s3.SecretsManager
import activesupport.config.Configuration
import com.typesafe.config.Config
import utils.SetUp.env

object Utilities {
  val random = new scala.util.Random
  val CONFIG: Config = new Configuration().getConfig

  def orderRef(): Int = random.nextInt(Integer.MAX_VALUE)

  def randomInt(): Int = random.between(1, 10)

  def password(): String = {
    (env) match {
      case "int" =>
     SecretsManager.getSecretValue(("intEnvPassword"));
      case _ =>
        "${Password}"
    }
  }
}