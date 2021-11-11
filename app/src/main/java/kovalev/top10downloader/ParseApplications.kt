package kovalev.top10downloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
  private val TAG = "parseApplications"
  val applications = ArrayList<FeedEntry>()

  fun parse(xmlData: String): Boolean {
    var status = true
    var inEntry = false
    var textValue = ""

    try {
      val factory = XmlPullParserFactory.newInstance()
      factory.isNamespaceAware = true
      val xpp = factory.newPullParser()
      xpp.setInput(xmlData.reader())
      var eventType = xpp.eventType
      var currentRecord = FeedEntry()
      while (eventType != XmlPullParser.END_DOCUMENT) {

      }

    } catch (e: Exception) {
      e.printStackTrace()
      status = false
    }
    return status
  }
}