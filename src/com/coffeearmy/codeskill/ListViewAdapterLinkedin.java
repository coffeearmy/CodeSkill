package com.coffeearmy.codeskill;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ListViewAdapterLinkedin extends ArrayAdapter<LinkedinItem> {

	ArrayList<LinkedinItem> skillusage;
	Context c;
	public ListViewAdapterLinkedin(Context context, int resource,
			ArrayList<LinkedinItem> objects) {
		super(context, resource, objects);
		this.skillusage=objects;
		this.c=context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		 try{
			    if (v == null) {
			     //Si no hay vista hay que crearla
			              
			    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			    v = vi.inflate(R.layout.linkedin_layout,null);
			    }
			    //Posicion tiene el item de la lista que se va
			
			    LinkedinItem o = skillusage.get(position);
			    if (o != null) {
			 
			    //poblamos la lista de elementos
			 
			    TextView tt = (TextView) v.findViewById(R.id.row_toptext);
			    TextView t2= (TextView) v.findViewById(R.id.row_bottomtext);
			    ProgressBar sp= (ProgressBar) v.findViewById(R.id.chartskill);
			 
		
			    t2.setText(Integer.toString(o.Percent));
			    tt.setText(o.SkillName);
			    sp.setProgress(o.Percent);
			    }
		 
			    }catch (Exception e) {
			        System.out.print(e);
			    }
			    return v;
			 
	}
	
	@Override
	public void setNotifyOnChange(boolean notifyOnChange) {
		// TODO Auto-generated method stub
		super.setNotifyOnChange(notifyOnChange);
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	


}

