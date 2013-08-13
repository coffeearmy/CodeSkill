package com.coffeearmy.codeskill;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TwitterItems extends AsyncTask<String, Integer, Integer>{
	 ArrayList<TweetItem> listItems;
	private ShowCode c;
	
	  public TwitterItems(ShowCode showCode) {
		c=showCode;
	}
	  
	  public Bitmap getBitmap(String bitmapUrl) {
		  try {
		    URL url = new URL(bitmapUrl);
		    return BitmapFactory.decodeStream(url.openConnection().getInputStream()); 
		  }
		  catch(Exception ex) {return null;}
		}
	  
	@Override
	protected Integer doInBackground(String... params) {
		listItems = new ArrayList<TweetItem>();
        StringBuilder builder = new StringBuilder();
        try {
            URL twitter = new URL(
                    "http://search.twitter.com/search.json?q=%23jobs%20"+params[0]+"&rpp=5");
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    tc.getInputStream()));
            String line;
			while ((line = in.readLine()) != null) {
				
				builder.append(line);
			}
            
            JSONObject jo = new JSONObject( builder.toString());

            JSONArray ja = new JSONArray(  jo.getString("results"));
            //String line;
            
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jaux = (JSONObject) ja.get(i);
                    TweetItem tweet= new TweetItem(jaux.getString("from_user"),
                    		jaux.getString("text"),
                    		jaux.getString("profile_image_url")
                    		);
                    listItems.add(tweet);
                    try{
						tweet.loadImagen(tweet.getImage_url());
					}catch(Exception e){
						Log.w("NET","La imagen no se ha cargado bien desde la URL",e);
					}

                  
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
		c.postPopulateTwitter();
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
	c.prePopulateTwitter();
		super.onPreExecute();
	}
}

