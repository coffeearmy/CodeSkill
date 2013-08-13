package com.coffeearmy.codeskill;

public class GHJobItem {
	private String title;
	private String location;
	private String type;
	private String company;
	private String url;
	
	public GHJobItem(String title, String location, String type,
			String company, String url) {
		
		this.title=title;
		this.location=location;
		this.type=type;
		this.company=company;
		this.url=url;
	}
	public String getLocation() {
		return location;
	}
	public String getCompany() {
		return company;
	}
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
