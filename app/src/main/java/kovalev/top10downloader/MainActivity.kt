package kovalev.top10downloader

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private val TAG = "MainActivity"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  private inner class DownloadData : AsyncTask<String, Void, String>() {
    private val TAG = "DownloadData"
    override fun doInBackground(vararg p0: String?): String {
      TODO("Not yet implemented")
    }

    override fun onPostExecute(result: String?) {
      super.onPostExecute(result)
    }
  }
}