package com.coffeearmy.codeskill;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class TweetItem {
	private String username;
		  private String message;
		  private String image_url;
		 private BitmapDrawable imagen;
			
		  public TweetItem(String username, String message, String url) {
		    this.username = username;
		    this.message = message;
		    this.image_url = url;
		  }
		  public void loadImagen(String url) throws MalformedURLException, IOException{
				InputStream is;
				
				is= new URL(url).openStream();
				setImagen(new BitmapDrawable(BitmapFactory.decodeStream(is)));
				
			}
		public String getMessage() {
			return message;
		}
		public String getImage_url() {
			return image_url;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setImagen(BitmapDrawable imagen) {
			this.imagen = imagen;
		}
		public BitmapDrawable getImagen() {
			return imagen;
		}
		}
