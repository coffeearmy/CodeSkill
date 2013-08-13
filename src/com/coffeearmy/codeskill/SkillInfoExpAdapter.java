package com.coffeearmy.codeskill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class SkillInfoExpAdapter extends SimpleExpandableListAdapter {

	private String PERCENT = "percent";
	private String skill;
	final String NAME = "name";
	final String IMAGE = "image";
	private LayoutInflater layoutInflater;
	private ShowCode c;
	private RelativeLayout fieldsLayout;
	private RelativeLayout progressLayout;
	private int grpPos;
	private ImageView imageview;
	private TextView txtfield;
	//list for save the groupviews.
	private List<View> mGroupViews;

	public SkillInfoExpAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo, String sll) {
		super(context, groupData, expandedGroupLayout, groupFrom, groupTo,
				childData, childLayout, childFrom, childTo);

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setC((ShowCode) context);
		
		this.skill=sll;
	}
	
	


	 public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
      
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.simple_expandable_list_item_1, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text1);
        switch (groupPosition) {
		case 0:// linkedin	
			c.setPrBarLinkedin((ProgressBar) convertView.findViewById(R.id.pbExpandable));
			tv.setText(R.string.LinkedINheader);
			((ImageView) convertView.findViewById(R.id.question)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					HelpDialogs.getLinkedINhelper(v.getContext());					
				}
			});
			//c.populateLinkedIN(skill);
			break;
		case 3:// StackOverFlow
			c.setPrBarstackoverflow((ProgressBar) convertView.findViewById(R.id.pbExpandable));
			tv.setText(R.string.StackOverflow);
			((ImageView) convertView.findViewById(R.id.question)).setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					HelpDialogs.getStackOverflowhelper(v.getContext());					
				}
			});
			break;
		case 1:// Github
			c.setPrBarGithub((ProgressBar) convertView.findViewById(R.id.pbExpandable));
			tv.setText(R.string.Github);
			((ImageView) convertView.findViewById(R.id.question)).setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					HelpDialogs.getGithubhelper(v.getContext());					
				}
			});
			break;
		case 2:// Github
			c.setPrBarGHJ((ProgressBar) convertView.findViewById(R.id.pbExpandable));
			tv.setText(R.string.githubJob);
			((ImageView) convertView.findViewById(R.id.question)).setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					HelpDialogs.getGithubJobhelper(v.getContext());					
				}
			});
			break;
		case 4:// Twitter
			c.setPrBarTwitter((ProgressBar) convertView.findViewById(R.id.pbExpandable));
			tv.setText(R.string.Twitter);
			((ImageView) convertView.findViewById(R.id.question)).setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					HelpDialogs.getTwitterflowhelper(v.getContext());					
				}
			});
			break;
		}
        return convertView;
    }


	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		
		if (getChildrenCount(groupPosition) == 0) {

			switch (groupPosition) {
			case 0:// Github
				getC().populateLinkedIN(skill);
				break;
			case 3:// StackOverFlow
				getC().populateStackOverFlow(skill);
				break;
			case 1:// Github
				getC().populateGithub(skill);
				break;
			case 2:// Github
				getC().populateGHJobs(skill);
				break;
			case 4:// Twitter
				getC().populateTwitter(skill);
				break;
			}
			super.onGroupExpanded(groupPosition);
			notifyDataSetChanged();

		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return super.getGroup(groupPosition);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// Populate your custom view here
		grpPos = groupPosition;
		View v = null;// super.getChildView(groupPosition, childPosition,
						// isLastChild,convertView, parent);
		Map<String, Object> map = (Map<String, Object>) getChild(groupPosition,
				childPosition);
		AssetManager assetManager = c.getAssets();
		Typeface tf = Typeface.createFromAsset(assetManager,"fonts/Roboto-BoldCondensed.ttf");		
		switch (groupPosition) {
		case 0: //LinkedIN
			v = layoutInflater.inflate(R.layout.linkedin_layout, null, false);			
			TextView tt = (TextView) v.findViewById(R.id.row_toptext);
			//TextView t2 = (TextView) v.findViewById(R.id.row_bottomtext);
			ProgressBar sp = (ProgressBar) v.findViewById(R.id.chartskill);

			String percent = (String) map.get(PERCENT);
			//t2.setText((String) map.get(PERCENT));
	
			int percentInt =setPercent(percent.substring(0, percent.indexOf(".")));
			tt.setText((((String) (map.get(NAME))).replaceAll("_", " ")+" - "+Integer.toString(percentInt)+"%") );
			sp.setProgress(percentInt);
			break;
		case 2: //GithubJobs
			v = layoutInflater.inflate(
					R.layout.ghjexpandablecell, null, false);
			((TextView) v.findViewById(R.id.tvjobTitle)).setText((String) map
					.get("title"));
			((TextView) v.findViewById(R.id.tvtypejob)).setText((String) map
					.get("type"));
			((TextView) v.findViewById(R.id.tvcompname)).setText((String) map
					.get("company"));
			((TextView) v.findViewById(R.id.tvlocation)).setText((String) map
					.get("localy"));
			txtfield=((TextView) v.findViewById(R.id.tvjobTitle));
			txtfield.setTypeface(tf);
			break;
		case 4: //Twitter
			v = layoutInflater.inflate(
					R.layout.expandable_list_item_with_image, null, false);
			((TextView) v.findViewById(R.id.name)).setText((String) map
					.get(NAME));
			txtfield=((TextView) v.findViewById(R.id.name));
			txtfield.setTypeface(tf);
			imageview = (ImageView) v.findViewById(R.id.image);
			imageview.setVisibility(View.GONE);
			Linkify.addLinks(((TextView) v.findViewById(R.id.name)),
					Linkify.ALL);
			imageview.setVisibility(View.VISIBLE);
			imageview = (ImageView) v.findViewById(R.id.image);
			imageview.setImageDrawable((Drawable) map.get(IMAGE));
			break;
		default:
			v = layoutInflater.inflate(
					R.layout.expandable_list_item_with_image, null, false);
			((TextView) v.findViewById(R.id.name)).setText((String) map
					.get(NAME));
			txtfield=((TextView) v.findViewById(R.id.name));
			txtfield.setTypeface(tf);
			imageview = (ImageView) v.findViewById(R.id.image);
			imageview.setVisibility(View.GONE);
			break;
		}
	/*	if (groupPosition != 0) {
			v = layoutInflater.inflate(
					R.layout.expandable_list_item_with_image, null, false);
			((TextView) v.findViewById(R.id.name)).setText((String) map
					.get(NAME));
			txtfield=((TextView) v.findViewById(R.id.name));
			AssetManager assetManager = c.getAssets();
			Typeface tf = Typeface.createFromAsset(assetManager,"fonts/Roboto-BoldCondensed.ttf");			
			txtfield.setTypeface(tf);
			imageview = (ImageView) v.findViewById(R.id.image);
			imageview.setVisibility(View.GONE);
			if (groupPosition == 3) {
				Linkify.addLinks(((TextView) v.findViewById(R.id.name)),
						Linkify.ALL);
				imageview.setVisibility(View.VISIBLE);
				imageview = (ImageView) v.findViewById(R.id.image);
				imageview.setImageDrawable((Drawable) map.get(IMAGE));
			}

			} else {
				v = layoutInflater.inflate(R.layout.linkedin_layout, null, false);
	
				TextView tt = (TextView) v.findViewById(R.id.row_toptext);
				TextView t2 = (TextView) v.findViewById(R.id.row_bottomtext);
				ProgressBar sp = (ProgressBar) v.findViewById(R.id.chartskill);
	
				String percent = (String) map.get(PERCENT);
				t2.setText((String) map.get(PERCENT));
				tt.setText(((String) map.get(NAME)).replaceAll("_", " "));
				sp.setProgress(setPercent(percent.substring(0, percent.indexOf("."))));
			}*/
		return v;
	}

	@Override
	public View newChildView(boolean isLastChild, ViewGroup parent) {
		if (grpPos == 0)
			return layoutInflater
					.inflate(R.layout.linkedin_layout, null, false);
		if (grpPos == 2)
			return layoutInflater
					.inflate(R.layout.ghjexpandablecell, null, false);
		else
			return layoutInflater.inflate(
					R.layout.expandable_list_item_with_image, null, false);
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	public Integer setPercent(String n) {
		int current = Integer.parseInt(n);
		int min = 0;
		int max = 200;
		int percentFinal = 0;
		if (current < 0) {
			// float percent = ((float)(current - min) / (max - min));
			int percentReverse = (int) (100 + current);
			percentFinal = percentReverse;
		} else {
			percentFinal = 100 + current;
		}
		int pFinal=(percentFinal*100)/200;
		return pFinal;

	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setC(ShowCode c) {
		this.c = c;
	}

	public ShowCode getC() {
		return c;
	}

}
/*********** DUMP CODE *************/
// Set up some sample data in both groups
/*
 * for( int i=0; i<10; ++i) { final HashMap<String, Object> map = new
 * HashMap<String,Object>(); map.put(NAME, "Child " + i ); map.put(IMAGE,
 * getResources().getDrawable(R.drawable.ic_action_search)); ( i%2==0 ?
 * group1data : group2data ).add(map); }
 * 
 * <ImageView android:id="@+id/image" android:layout_width="wrap_content"
 * android:layout_height="wrap_content" />
 */
/*
 * {
 * 
 * @Override public void onGroupExpanded(int groupPosition) { // TODO
 * Auto-generated method stub super.onGroupExpanded(groupPosition);
 * 
 * if (getChildrenCount(groupPosition) == 0) {
 * 
 * switch (groupPosition) { case 1:// StackOverFlow
 * populateStackOverFlow(skill); break; case 0:// Github populateGithub(skill);
 * break; case 2:// Twitter populateTwitter(skill); break; }
 * 
 * } }
 * 
 * @Override public View getChildView(int groupPosition, int childPosition,
 * boolean isLastChild, View convertView, ViewGroup parent) { // Populate your
 * custom view here
 * 
 * View v = super.getChildView(groupPosition, childPosition, isLastChild,
 * convertView, parent); Map<String, Object> map = (Map<String, Object>)
 * getChild( groupPosition, childPosition); ((TextView)
 * v.findViewById(R.id.name)).setText((String) map .get(NAME)); //
 * ((ImageView)v.findViewById(R.id.image)).setImageDrawable( // (Drawable)
 * ((Map<String,Object>)getChild(groupPosition, // childPosition)).get(IMAGE) );
 * 
 * return v; }
 * 
 * @Override public View newChildView(boolean isLastChild, ViewGroup parent) {
 * return layoutInflater.inflate( R.layout.expandable_list_item_with_image,
 * null, false); }
 * 
 * @Override public void notifyDataSetChanged() { // TODO Auto-generated method
 * stub super.notifyDataSetChanged(); }
 * 
 * };
 */
