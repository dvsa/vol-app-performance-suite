package utils

import org.jsoup.Jsoup
import org.jsoup.parser.Parser

object Utilities {
  def htmlTableRowCounter(table: String): String = {
    val document = Jsoup.parse(table, "", Parser.xmlParser)
    val element = document.select("tr.row")
    String.valueOf(element.size)
  }
}