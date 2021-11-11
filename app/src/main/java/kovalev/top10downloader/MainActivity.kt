package kovalev.top10downloader

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

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
    val downloadData = DownloadData()
    downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
  }

  companion object {
    private class DownloadData : AsyncTask<String, Void, String>() {
      private val TAG = "DownloadData"
      override fun doInBackground(vararg url: String): String {
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
          Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
      }

      override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d(TAG, "some data ${result}")
      }

      private fun downloadXML(urlPath: String): String {
        return URL(urlPath).readText()
      }
    }
  }
}
