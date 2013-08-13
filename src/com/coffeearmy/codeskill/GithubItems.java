package com.coffeearmy.codeskill;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class GithubItems extends AsyncTask<String, Integer, Integer> {

	public Integer numRepositorios;
	public Integer numUsers;
	public Integer numLines;
	private ShowCode c;

	public GithubItems(ShowCode showCode) {
		c=showCode;
	}

	@SuppressLint("ParserError")
	public Integer getNumLines() {
		return numLines;
	}

	public Integer getNumRepositorios() {
		return numRepositorios;
	}

	public Integer getNumUsers() {
		return numUsers;
	}

	@Override
	protected Integer doInBackground(String... params) {
		try {
			String url = "https://github.com/search?q="
					+ params[0]
					+ "&repo=&langOverride=&start_value=1&type=Everything&language="
					+ params[0];

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet, localContext);
			String result = "";

			// Guardamos en la variable reader el HTML
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
		
			String line = null;
			int vez = 0;
			Pattern patron = Pattern.compile("<div class=\"title\">.*");
			// Leemos cada linea y buscamos el patron
			while ((line = reader.readLine()) != null && vez != 3) {
				// Buscamos una imagen
				
				Matcher matcher = patron.matcher(line);

				// Hace que Matcher busque los trozos.
				if (matcher.find()) // hay imagen
				{
					if (line.contains("Repositories"))
						numRepositorios = Integer.parseInt((String) line
								.substring(line.indexOf("(") + 1,
										line.indexOf(")")));
					else if (line.contains("Users")) {
						numUsers = Integer.parseInt((String) line.substring(
								line.indexOf("(") + 1, line.indexOf(")")));
					} else if (line.contains("Source"))
						numLines = Integer.parseInt((String) line.substring(
								line.indexOf("(") + 1, line.indexOf(")")));
					// Guardamos en el array los nombres de las imagenes y
					// hacemos que deje de recorrer el fichero.

					vez = vez + 1;

				}
				// Si se recorriera el fichero compelo en result tendriamos el
				// HTML.			

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
		c.postPopulateGithub();
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
		c.prePopulateGithub();
		super.onPreExecute();
	}
}
