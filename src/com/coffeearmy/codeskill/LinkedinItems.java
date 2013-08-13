package com.coffeearmy.codeskill;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LinkedinItems extends AsyncTask<String, Integer, Integer>{
	private ShowCode c;
	final String NAME = "name";
	final String PERCENT = "percent";	
	private ArrayList<HashMap<String, Object>> groupLinkedin;
	public LinkedinItems(ShowCode cp) {
		this.c=cp;
		groupLinkedin=new ArrayList<HashMap<String, Object>>();
		
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		try {
			URL text = new URL(
					"http://www.linkedin.com/skills/api/v1/charts/related_growth/"
							+ params[0]);// Objective-C"
			XmlPullParserFactory parserCreator = XmlPullParserFactory
					.newInstance();
			XmlPullParser parser = parserCreator.newPullParser();
			parser.setInput(text.openStream(), null);
			int parserEvent = parser.getEventType();
			while (parserEvent != XmlPullParser.END_DOCUMENT) {
				switch (parserEvent) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String tag = parser.getName();
					if (tag.equalsIgnoreCase("set")) {						
						
						String skillName = parser.getAttributeValue(null,
								"link");
						String skillvalue = parser.getAttributeValue(null,
								"value");
						Log.i("XML", "value" + skillvalue + "  line:"
								+ skillName);
						
						final HashMap<String, Object> mapLK = new HashMap<String, Object>();
						mapLK.put(NAME,(String) skillName.subSequence(37, skillName.indexOf("?")) );
						mapLK.put(PERCENT,skillvalue );					
						groupLinkedin.add(mapLK);					
						
					}

				case XmlPullParser.END_TAG:
					break;
				}
				parserEvent = parser.next();
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
		
		c.postPopulateLinkedIn(groupLinkedin);
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
		c.prePopulateLinkedIN();
		super.onPreExecute();
	}
}
