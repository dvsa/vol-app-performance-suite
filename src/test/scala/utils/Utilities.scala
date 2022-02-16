package utils

import activesupport.config.Configuration
import com.typesafe.config.Config
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import utils.SetUp.env

object Utilities {
  val random = new scala.util.Random
  val CONFIG: Config = new Configuration().getConfig

  def htmlTableRowCounter(table: String): String = {
    val document = Jsoup.parse(table, "", Parser.xmlParser)
    val element = document.select("tr.row")
    String.valueOf(element.size)
  }
  def orderRef(): Int = random.nextInt(Integer.MAX_VALUE)
  def randomInt(): Int = random.between(1,10)

  def password(): String = {
      (env) match {
        case "int" =>
          CONFIG.getString("intPassword")
        case _=>
           "${Password}"
      }
  }
}