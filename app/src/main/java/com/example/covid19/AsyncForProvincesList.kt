package com.example.covid19

import android.os.AsyncTask
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.covid19.entity.Province
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.time.LocalDate

class AsyncForProvincesList(private val context: CovidInfoActivity, private val country: String) :
    AsyncTask<String, Void, JSONObject>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doInBackground(vararg params: String?): JSONObject? {
        val client = OkHttpClient()
        var jsonObject: JSONObject? = null

        val request = Request.Builder()
            .url("https://api.covid19api.com/live/country/$country")
            .get()
            .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.body().string()
            var data = "{ provinces : $body }"
            jsonObject = JSONObject(data)
        }

        return jsonObject
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        var provincesList = mutableListOf<Province>()
        if (result != null) {
            val jsonArray = result.getJSONArray("provinces")
            val today = LocalDate.now().toString()
            for (index in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(index)
                if (jsonObject.getString("Date").substring(0, 10).equals(today)) {
                    val provinceName = jsonObject.getString("Province")
                    val active = jsonObject.getInt("Active")
                    val recovered = jsonObject.getInt("Recovered")
                    val confirmed = jsonObject.getInt("Confirmed")
                    val deaths = jsonObject.getInt("Deaths")
                    val province = Province(provinceName, confirmed, recovered, deaths, active)
                    provincesList.add(province)
                }
            }

        }


    }
}
