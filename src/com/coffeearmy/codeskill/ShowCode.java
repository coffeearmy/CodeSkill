package com.coffeearmy.codeskill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.widget.SearchView;

public class ShowCode extends SherlockListActivity {
	ListView lstSkillSearch;
	// textview de la skill qe se esta buscando
	TextView lblSkill;
	// El expandableView que contiene toda la info
	ExpandableListView explstmoreinfo;
	// List1 contiene
	private ArrayList<LinkedinItem> list1;
	// En el arraylist se tienen los obetos que se quiern mostrar
	private ArrayList<HashMap<String, Object>> groupGitHub;
	private ArrayList<HashMap<String, Object>> groupStackOverflow;
	private ArrayList<HashMap<String, Object>> groupTwitter;
	private ArrayList<HashMap<String, Object>> groupLinked;
	private ArrayList<HashMap<String, Object>> groupGHJobs;
	
	final String NAME = "name";
	final String IMAGE = "image";
	private String skill;
	private SkillInfoExpAdapter sExpandLstAdapter;
	
	private LinkedinItems linkedinAsinc;
	private GithubItems github;
	private StackOverflowItems stackOF;
	private TwitterItems tw;
	private GHJobItems gHJobs;
	
	
	private TextView txtvSkill;
	private ProgressBar prBarGithub;
	private ProgressBar prBarLinkedin;
	private ProgressBar prBarstackoverflow;
	private ProgressBar prBarTwitter;
	private ProgressBar prBarGHJ;
	private String[] autocompleteItems;
	private Context c;

	public void clearExpandableLst() {
		groupGitHub.clear();
		groupStackOverflow.clear();
		groupTwitter.clear();
		groupLinked.clear();
		groupGHJobs.clear();
		sExpandLstAdapter.setSkill(skill);
		sExpandLstAdapter.notifyDataSetChanged();
	}

	public LinkedinItems getLinkedinAsinc() {
		return linkedinAsinc;
	}

	public ArrayList<LinkedinItem> getList1() {
		return list1;
	}

	public ListView getLstSkillSearch() {
		return lstSkillSearch;
	}

	public ProgressBar getPrBarGithub() {
		return prBarGithub;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.activity_show_code);
			c=this;
			lstSkillSearch = (ListView) findViewById(android.R.id.list);
			txtvSkill = (TextView) findViewById(R.id.txtvskill);
			explstmoreinfo = (ExpandableListView) findViewById(R.id.moreinfo);

			// Recuperamos el bundle del intent
			Bundle bundle = getIntent().getExtras();

			// Y así extraemos la información que hay dentro

			skill = bundle.getString("SearchSkill");
			txtvSkill.setText(skill.toUpperCase());
			// CAMBIAMOS LA FUENTE
			AssetManager assetManager = getBaseContext().getAssets();
			Typeface tf = Typeface.createFromAsset(assetManager,
					"fonts/Roboto-BoldCondensed.ttf");
			txtvSkill.setTypeface(tf);

			list1 = new ArrayList<LinkedinItem>();
	
			//populateLinkedIN(skill);
			setSkill(skill);
			// Inicializar expandable lists
			// Construct Expandable List

			final ArrayList<HashMap<String, String>> headerData = new ArrayList<HashMap<String, String>>();
			// HEADER names
			final HashMap<String, String> grpLinkedin = new HashMap<String, String>();
			grpLinkedin.put(NAME, getString(R.string.LinkedINheader));
			headerData.add(grpLinkedin);

			final HashMap<String, String> grpGH = new HashMap<String, String>();
			grpGH.put(NAME, getString(R.string.Github));
			headerData.add(grpGH);
			
			final HashMap<String, String> grpGHJob = new HashMap<String, String>();
			grpGHJob.put(NAME, getString(R.string.Github));
			headerData.add(grpGHJob);			

			final HashMap<String, String> grpFan = new HashMap<String, String>();
			grpFan.put(NAME, getString(R.string.StackOverflow));
			headerData.add(grpFan);

			final HashMap<String, String> grptwt = new HashMap<String, String>();
			grptwt.put(NAME, getString(R.string.Twitter));
			headerData.add(grptwt);

			// final HashMap<String, String> grpwik = new HashMap<String,
			// String>();
			// grpwik.put(NAME, getString(R.string.Wikipedia));
			// headerData.add(grpwik);
			// Child names

			final ArrayList<ArrayList<HashMap<String, Object>>> childData = new ArrayList<ArrayList<HashMap<String, Object>>>();

			groupLinked = new ArrayList<HashMap<String, Object>>();
			childData.add(groupLinked);

			groupGitHub = new ArrayList<HashMap<String, Object>>();
			childData.add(groupGitHub);
			groupGHJobs = new ArrayList<HashMap<String, Object>>();
			childData.add(groupGHJobs);

			groupStackOverflow = new ArrayList<HashMap<String, Object>>();
			childData.add(groupStackOverflow);

			groupTwitter = new ArrayList<HashMap<String, Object>>();
			childData.add(groupTwitter);

			// groupWiki = new ArrayList<HashMap<String, Object>>();
			// childData.add(groupWiki);

			sExpandLstAdapter = new SkillInfoExpAdapter(this, headerData,
					R.layout.simple_expandable_list_item_1,
					new String[] { NAME }, new int[] { android.R.id.text1 },
					childData, 0, null, new int[] {},skill);
			explstmoreinfo.setAdapter(sExpandLstAdapter);
			
			explstmoreinfo.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					if (groupPosition == 0) {
						Map<String, Object> map = (Map<String, Object>) sExpandLstAdapter
								.getChild(groupPosition, childPosition);
						String skill = ((String) map.get(NAME)).toString();
						setSkill(skill);
						linkedinAsinc = new LinkedinItems(sExpandLstAdapter
								.getC());
						populateLinkedIN(skill);
						sExpandLstAdapter.notifyDataSetChanged();
						clearExpandableLst();
						txtvSkill.setText(skill.toUpperCase());
						txtvSkill.refreshDrawableState();
					}
					return false;
				}
			});
		} catch (Exception e) {
			Log.e("ERR", e.getMessage());
		}
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
	    if (item.getItemId() == R.id.menu_about) {
	    	 HelpDialogs.getAboutUs(c);
	    }	   
	    return true;
	}
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		
		SearchView sv = new SearchView(getSupportActionBar().getThemedContext());
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);     
		sv.setQueryHint("Search a code language...");
		 getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		
		EditText search = (EditText) sv.findViewById(R.id.abs__search_src_text);
		search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event != null && event.getAction() != KeyEvent.ACTION_DOWN)
					return false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH || event == null
						|| event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					Toast.makeText(getBaseContext(), v.getText(),
							Toast.LENGTH_LONG).show();
					if (!v.getText().toString().equals("")) {
						String skill = v.getText().toString();
						setSkill(skill);
						linkedinAsinc = new LinkedinItems(sExpandLstAdapter
								.getC());
						populateLinkedIN(skill);
						sExpandLstAdapter.notifyDataSetChanged();
						clearExpandableLst();
						txtvSkill.setText((skill).toUpperCase());
						txtvSkill.refreshDrawableState();
					}
					return true;
				}
				return false;
			}
		});

		menu.add(0, 2, 2, "Search")
				.setIcon(R.drawable.ic_action_search)
				.setActionView(sv)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_ALWAYS
								| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	
	void populateGHJobs(String skill) {
		/** Github **/
		gHJobs = new GHJobItems(this);
		gHJobs.execute(new String[] { skill });
	}
	
	void populateGithub(String skill) {
		/** Github **/
		github = new GithubItems(this);
		github.execute(new String[] { skill });
	}

	public void populateLinkedIN(String skill) {
		/** LinkedIN **/
		linkedinAsinc = new LinkedinItems(this);
		linkedinAsinc.execute(new String[] { skill });
	}

	void populateStackOverFlow(String skill) {
		/** StackOverflow **/
		stackOF = new StackOverflowItems(this);
		stackOF.execute(new String[] { skill });
	}

	void populateTwitter(String skill) {
		/** Twitter **/
		tw = new TwitterItems(this);
		tw.execute(new String[] { skill });
	}

	public void postPopulateGHJobs() {
		for (int i = 0; i < gHJobs.listItems.size(); ++i) {
			final HashMap<String, Object> mapGHJob = new HashMap<String, Object>();
			mapGHJob.put("title", gHJobs.listItems.get(i).getTitle());
			mapGHJob.put("company", gHJobs.listItems.get(i).getCompany());
			mapGHJob.put("type", gHJobs.listItems.get(i).getType());
			mapGHJob.put("url", gHJobs.listItems.get(i).getUrl());
			mapGHJob.put("localy", gHJobs.listItems.get(i).getLocation());
			groupGHJobs.add(mapGHJob);
		}
		sExpandLstAdapter.notifyDataSetChanged();
		Toast.makeText(this, "termina GithubjOBS", Toast.LENGTH_SHORT).show();
		prBarGHJ.setVisibility(View.GONE);
		//groupGHJobs
	}

	void postPopulateGithub() {
		final HashMap<String, Object> mapGH = new HashMap<String, Object>();
		mapGH.put(NAME, github.getNumRepositorios()
				+ " repositories.");
		groupGitHub.add(mapGH);
		final HashMap<String, Object> mapGH2 = new HashMap<String, Object>();
		mapGH2.put(NAME, github.getNumUsers() + " users.");
		groupGitHub.add(mapGH2);
		final HashMap<String, Object> mapGH3 = new HashMap<String, Object>();
		mapGH3.put(NAME, github.getNumLines() + " lines.");
		groupGitHub.add(mapGH3);
		sExpandLstAdapter.notifyDataSetChanged();
		//progressDialog.dismiss();
		Toast.makeText(this, "termina Github", Toast.LENGTH_SHORT).show();
		prBarGithub.setVisibility(View.GONE);
	}

	void postPopulateLinkedIn(ArrayList<HashMap<String, Object>> groupLinkedin) {
		groupLinked.addAll(groupLinkedin);
		sExpandLstAdapter.notifyDataSetChanged();
		//progressDialog.dismiss();
		prBarLinkedin.setVisibility(View.GONE);
		Toast.makeText(this, "termina linkedin", Toast.LENGTH_SHORT).show();
	}

	void postPopulateStackOverFlow() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(NAME, stackOF.getNumberofpost() + " posts.");
		groupStackOverflow.add(map);
		sExpandLstAdapter.notifyDataSetChanged();
		//progressDialog.dismiss();
		prBarstackoverflow.setVisibility(View.GONE);
		Toast.makeText(this, "termina stack", Toast.LENGTH_SHORT).show();
	}

	void postPopulateTwitter() {
		for (int i = 0; i < tw.listItems.size(); ++i) {
			final HashMap<String, Object> mapTw = new HashMap<String, Object>();
			mapTw.put(NAME, tw.listItems.get(i).getMessage());
			mapTw.put(IMAGE, tw.listItems.get(i).getImagen());
			groupTwitter.add(mapTw);
		}
		sExpandLstAdapter.notifyDataSetChanged();
		//progressDialog.dismiss();
		Toast.makeText(this, "termina twitter", Toast.LENGTH_SHORT).show();
		prBarTwitter.setVisibility(View.GONE);
	}

	public void prePopulateGHJobs() {
		Toast.makeText(this, "inicia GHJ", Toast.LENGTH_SHORT).show();
		getPrBarGHJ().setVisibility(View.VISIBLE);
		
	}

	void prePopulateGithub() {
				Toast.makeText(this, "inicia Github", Toast.LENGTH_SHORT).show();
		 prBarGithub.setVisibility(View.VISIBLE);
	}

	void prePopulateLinkedIN() {
	
		Toast.makeText(this, "inicia linkedin", Toast.LENGTH_SHORT).show();
		prBarLinkedin.setVisibility(View.VISIBLE);

	}

	void prePopulateStackOverFlow() {

		Toast.makeText(this, "inicia stack", Toast.LENGTH_SHORT).show();
		prBarstackoverflow.setVisibility(View.VISIBLE);

	}

	void prePopulateTwitter() {
	
		Toast.makeText(this, "inicia twitter", Toast.LENGTH_SHORT).show();
		prBarTwitter.setVisibility(View.VISIBLE);
	}
	public void setPrBarGithub(ProgressBar prBarGithub) {
		this.prBarGithub = prBarGithub;
	}
	public void setPrBarLinkedin(ProgressBar prBarLinkedin) {
		this.prBarLinkedin = prBarLinkedin;
	}
	public void setPrBarstackoverflow(ProgressBar prBarstackoverflow) {
		this.prBarstackoverflow = prBarstackoverflow;
	}

	public void setPrBarTwitter(ProgressBar prBarTwitter) {
		this.prBarTwitter = prBarTwitter;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setPrBarGHJ(ProgressBar prBarGHJ) {
		this.prBarGHJ = prBarGHJ;
	}

	public ProgressBar getPrBarGHJ() {
		return prBarGHJ;
	}
	
 
}
