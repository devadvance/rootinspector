package com.devadvance.rootinspector;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;

public class Main extends ListActivity {
	String[] menuItems;
	public final static String TAG = "RootInspector";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		menuItems = res.getStringArray(R.array.menu_array);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuItems);
		setListAdapter(adapter);
	}
	
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent;
		switch (position) {
		case 0:
			intent = new Intent(this, InspectUsingJava.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(this, InspectUsingNative.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
