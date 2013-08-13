package com.coffeearmy.codeskill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class HelpDialogs {
	static AlertDialog dialog ;
	static void getLinkedINhelper( Context c){		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("LinkedIN");
		builder.setMessage(R.string.descLinkedin);
		builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int position) {
		            }
		        });
		        dialog = builder.create();
		        dialog.show();				
	}
	static void getGithubhelper( Context c){		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("Github");
		builder.setMessage(R.string.descGithub);
		builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int position) {
		            }
		        });
		        dialog = builder.create();
		        dialog.show();				
	}
	static void getGithubJobhelper( Context c){		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("Github:job");
		builder.setMessage(R.string.descGithub);
		builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int position) {
		            }
		        });
		        dialog = builder.create();
		        dialog.show();				
	}
	
	static void getStackOverflowhelper( Context c){		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("StackOverflow");
			builder.setMessage(R.string.descGithub);
			builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int position) {
			            }
			        });
		        dialog = builder.create();
		        dialog.show();				
	}
	static void getTwitterflowhelper( Context c){		
	
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("Twitter");
		builder.setMessage(R.string.descTwitter);
		builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int position) {
		            }
		        });
		        dialog = builder.create();
		        dialog.show();				
	}
	static void getAboutUs( Context c){		
		
		AlertDialog.Builder builder=new AlertDialog.Builder(c);
		builder.setTitle("About us ");
		builder.setMessage(R.string.about_us);
		builder.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int position) {
		            }
		        });
		        dialog = builder.create();
		        dialog.show();				
	}
}
