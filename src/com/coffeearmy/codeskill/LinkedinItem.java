package com.coffeearmy.codeskill;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class LinkedinItem {
	String SkillName;
	String URLSkill;
	int Percent;

	public LinkedinItem(String percent,String URL) {
		try {
					
		this.Percent=Integer.parseInt(percent.substring(0, percent.indexOf(".")));
		this.URLSkill=URL;
		this.SkillName=(String) URL.subSequence(37, URL.indexOf("?"));
		} catch (Exception e) {
			Log.e("ERR", "Error at Linkedin parsing: "+e.getMessage());
		}
	}
	public int getPercent() {
		return Percent;
	}
	public String getSkillName() {
		return SkillName;
	}	
	public void setPercent(int percent) {
		Percent = percent;
	}
	public void setSkillName(String skillName) {
		SkillName = skillName;
	}
	public String getURLSkill() {
		return URLSkill;
	}
	public void setURLSkill(String uRLSkill) {
		URLSkill = uRLSkill;
	}
	

}
