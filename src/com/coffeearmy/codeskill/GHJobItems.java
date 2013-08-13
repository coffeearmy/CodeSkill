package com.coffeearmy.codeskill;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GHJobItems extends AsyncTask<String, Integer, Integer>{
		ArrayList<GHJobItem> listItems;
		private ShowCode c;
		
		
		
		public GHJobItems(ShowCode showCode) {
			this.c=showCode;
		}

		@Override
		protected Integer doInBackground(String... arg0) {			
		listItems = new ArrayList<GHJobItem>();
        StringBuilder builder = new StringBuilder();
        try {
            URL gitHubJob = new URL(
                    "https://jobs.github.com/positions.json?description="+arg0[0]+"&page=1");
            URLConnection ghjc = gitHubJob.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
            		ghjc.getInputStream()));
            String line;
			/*while ((line = in.readLine()) != null) {
				
				builder.append(line);	
			}*/
            String json = in.readLine();
            JSONTokener tokener = new JSONTokener(json);
           // JSONArray finalResult = new JSONArray(tokener);
           // JSONObject jo = new JSONObject( in.readLine());

            JSONArray ja = new JSONArray( tokener);
            //String line;
            
                for (int i = 0; i <5; i++) {
                    JSONObject jaux = (JSONObject) ja.get(i);
                    GHJobItem jobItem= new GHJobItem(jaux.getString("title"),
                    		jaux.getString("location"),
                    		jaux.getString("type"),
                    		jaux.getString("company"),
                    		jaux.getString("url")
                    		);
                    listItems.add(jobItem);                  
                }
            
        }catch (FileNotFoundException e) {
			Log.e("Net", "Error in Network call", e);	
			return 1;
		}
		 catch (Exception e) {
			Log.e("Net", "Error in Network call", e);		
			return 2;
		}
		return 0;
	}
	 
	@Override
	protected void onPostExecute(Integer result) {
		c.postPopulateGHJobs();
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
	c.prePopulateGHJobs();
		super.onPreExecute();
	}
}