package com.bebetterprogrammer.trecox.networking;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URI;


public class DataBaseConnection extends AsyncTask<String, String, String> {


    public static String res;

    public void setRes(String res) {
        DataBaseConnection.res = res;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int success = jsonObject.getInt("success");
            if (success == 1) {
                JSONArray company = jsonObject.getJSONArray("company");
                JSONObject cmp = company.getJSONObject(0);
                int id = cmp.getInt("id");
                setRes(cmp.getString("company_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String... strings) {
        String result1 = "";
        String host = "http://trecox.epizy.com/toandroid.php";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(host));
            HttpResponse response = httpClient.execute(request);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder stringBuffer = new StringBuilder("");
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            result1 = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRes("hap");
        return result1;
    }
}
