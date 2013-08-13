package com.coffeearmy.codeskill;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.support.v4.app.NavUtils;


public class MainActivity extends Activity {

	//private AutoCompleteTextView autocomplete;
	String autocompleteItems [];
	AutoCompleteTextView txtcodesearch;
	Context c;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=this;
        autocompleteItems=getResources().getStringArray(R.array.code);
        txtcodesearch=(AutoCompleteTextView) findViewById(R.id.fldcodesearch);
        ArrayAdapter<String> adapter = 
            new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autocompleteItems);
        txtcodesearch.setAdapter(adapter);
        //Cuando en el editor se introduce un intro realiza la busqueda.
        txtcodesearch.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				 if (event != null && event.getAction() != KeyEvent.ACTION_DOWN)
				        return false;
				    if (actionId == EditorInfo.IME_ACTION_SEARCH
				        || event == null
				        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				    
				    		Intent goSearch = new Intent(MainActivity.this,ShowCode.class);
							Bundle bundle = new Bundle();
					        bundle.putString("SearchSkill", txtcodesearch.getText().toString());
					        goSearch.putExtras(bundle);
							startActivity(goSearch);
				    		
				    		return true; 					 
				  }
				return false;
			}
			
		});
           
    }
    
    
//Se crea el menu 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem aboutMenuItem = menu.findItem(R.id.menu_about);
        aboutMenuItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				 HelpDialogs.getAboutUs(c);
				return false;
			}
		});
    	//menu.add(0,2,2,"Search").setIcon(R.drawable.ic_action_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu((Menu) menu);
    }
   
   
    
}
