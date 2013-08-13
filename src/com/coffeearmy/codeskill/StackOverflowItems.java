package com.coffeearmy.codeskill;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class StackOverflowItems extends AsyncTask<String, Integer, Integer>{
	public int numberofpost;
	private ShowCode c;
	
	
	public StackOverflowItems(ShowCode showCode) {
		c=showCode;
	}

	public int getNumberofpost() {
		return numberofpost;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String nameSkill=params[0];
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("https://api.stackexchange.com/2.0/tags/"+nameSkill+"/info?order=desc&sort=popular&site=stackoverflow");
		//httpGet.setHeader("charset", "utf-8");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				GZIPInputStream zis = new GZIPInputStream(content);
				 	
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(StackOverflowItems.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return 2;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		String finalJSON=builder.toString();
		String StackOverflowItems= finalJSON.substring(finalJSON.indexOf("["),finalJSON.indexOf(",\"quota") );		
		
		try {
			JSONArray jsonArray = new JSONArray(StackOverflowItems);
			Log.i(StackOverflowItems.class.getName(),
					"Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Log.i(StackOverflowItems.class.getName(), jsonObject.getString("count"));
				numberofpost=numberofpost+Integer.parseInt( jsonObject.getString("count"));
			}
		}catch (Exception e) {
			Log.e("Net", "Error in Network call", e);		
			return 2;
		}
		return 0;
	}
	@Override
	protected void onPostExecute(Integer result) {
		c.postPopulateStackOverFlow();
		if (result==1){
			Toast toast = Toast.makeText(c, R.string.skillnotfound, Toast.LENGTH_LONG);
			toast.show();
			
		}else if(result==2){		
			Toast toast = Toast.makeText(c, R.string.Networkerror, Toast.LENGTH_LONG);
			toast.show();
			
		}
	}
	
	@Override
	protected void onPreExecute() {
	c.prePopulateStackOverFlow();
	
		super.onPreExecute();
	}
}

