package kovalev.top10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
  var name: String = ""
  var artist: String = ""
  var releaseDate: String = ""
  var summary: String = ""
  var imageURL: String = ""

  override fun toString(): String {
    return """
      name = $name
      artist = $artist
      releaseDate = $releaseDate
      imageURL = $imageURL
      """.trimIndent()
  }
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val downloadData = DownloadData(this, findViewById(R.id.xmlListView))
    downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
  }

  companion object {
    private class DownloadData(context: Context, listView: ListView) :
      AsyncTask<String, Void, String>() {
      private val TAG = "DownloadData"

      var propContext: Context by Delegates.notNull()
      var propListView: ListView by Delegates.notNull()

      init {
        propContext = context
        propListView = listView
      }

      override fun doInBackground(vararg url: String): String {
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
          Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
      }

      override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        val parseApplications = ParseApplications()
        parseApplications.parse(result)

        val arrayAdapter =
          ArrayAdapter(propContext, R.layout.list_item, parseApplications.applications)
        propListView.adapter = arrayAdapter
      }

      private fun downloadXML(urlPath: String): String {
        return URL(urlPath).readText()
      }
    }
  }
}
