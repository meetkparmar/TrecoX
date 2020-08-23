package com.bebetterprogrammer.trecox.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bebetterprogrammer.trecox.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI

class HomeFragment : Fragment() {

    val data = DataBase()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.company_tv.text = data.res

        return view
    }

    class DataBase : AsyncTask<String, String, String>()
    {

           var res : String = "kk"

        override fun doInBackground(vararg params: String?): String {
            var result1 = ""
            val host = "http://trecox.epizy.com/toandroid.php"

            try {
                val httpClient: HttpClient = DefaultHttpClient()
                val request = HttpGet()
                request.uri = URI(host)
                val response = httpClient.execute(request)
                val bufferedReader = BufferedReader(
                    InputStreamReader(
                        response.entity.content
                    )
                )
                val stringBuffer = StringBuilder("")
                var line: String? = ""
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuffer.append(line)
                }
                bufferedReader.close()
                result1 = stringBuffer.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result1
        }

        override fun onPostExecute(result: String) {
            try {
                val jsonObject = JSONObject(result)
                val success = jsonObject.getInt("success")
                if (success == 1) {
                    val company = jsonObject.getJSONArray("company")
                    val cmp = company.getJSONObject(0)
                    val id = cmp.getInt("id")
                    res = cmp.getString("company_name");
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
}