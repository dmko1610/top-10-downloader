package kovalev.top10downloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
  private val TAG = "parseApplications"
  val applications = ArrayList<FeedEntry>()

  fun parse(xmlData: String): Boolean {
    var status = true
    var inEntry = false
    var gotImage = false
    var textValue = ""

    try {
      val factory = XmlPullParserFactory.newInstance()
      factory.isNamespaceAware = true
      val xpp = factory.newPullParser()
      xpp.setInput(xmlData.reader())
      var eventType = xpp.eventType
      var currentRecord = FeedEntry()
      while (eventType != XmlPullParser.END_DOCUMENT) {
        val tagName = xpp.name?.lowercase()
        when (eventType) {
          XmlPullParser.START_TAG -> {
            if (tagName == "entry") {
              inEntry = true
            } else if ((tagName == "image") && inEntry) {
              val imageResolution = xpp.getAttributeValue(null, "height")
              if (imageResolution.isNotEmpty()) {
                gotImage = imageResolution == "53"
              }
            }
          }
          XmlPullParser.TEXT -> textValue = xpp.text
          XmlPullParser.END_TAG -> {
            if (inEntry) {
              when (tagName) {
                "entry" -> {
                  applications.add(currentRecord)
                  inEntry = false
                  currentRecord = FeedEntry()
                }
                "name" -> currentRecord.name = textValue
                "artist" -> currentRecord.artist = textValue
                "releaseDate" -> currentRecord.releaseDate = textValue
                "summary" -> currentRecord.summary = textValue
                "image" -> if (gotImage) currentRecord.imageURL = textValue
              }
            }
          }
        }
        eventType = xpp.next()
      }
      for (app in applications) {

      }

    } catch (e: Exception) {
      e.printStackTrace()
      status = false
    }
    return status
  }
}