package com.devadvance.rootinspector;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class InspectUsingJava extends Activity {
	ListView listView;
	CustomAdapter adapter;
	
	PackageManager pm;
	ActivityManager manager;
	String[] detectionTitles;
	String[] detectionDescriptions;
	String[] detectionFailures;
	String passString;
	String failString;
	ProgressDialog progDailog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspect_using_java);
		// Show the Up button in the action bar.
		setupActionBar();
		listView = (ListView) findViewById(R.id.listViewJava);
		
		//doChecking();
		DetectRoot task = new DetectRoot(InspectUsingJava.this);
		task.execute("Start");
		
		
		
	}
	
	private ArrayList<RootMethodModel> doChecking() {
		ArrayList<RootMethodModel> values;
		pm = getPackageManager();
		manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		Resources res = getResources();
		detectionDescriptions = res.getStringArray(R.array.java_detection_types_array);
		detectionTitles = res.getStringArray(R.array.java_detection_titles);
		detectionFailures= res.getStringArray(R.array.java_detection_failures_array);
		passString = res.getString(R.string.pass_string);
		failString = res.getString(R.string.failure_string);
		
		values = new ArrayList<RootMethodModel>();
		
		for (int i = 0; i < detectionTitles.length; i++) {
			values.add(checkRootUsingMethod(i));
		}
		
		return values;
	}
	
	public RootMethodModel checkRootUsingMethod(int method) {
		Root root = new Root(pm, manager);
		RootMethodModel rmm = new RootMethodModel("Invalid method.", "Invalid.", "N/A"); 
		
		switch(method) {
		case 0:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod0())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 1:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod1())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 2:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod2())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 3:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod3())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 4:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod4())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 5:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod5())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 6:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod6())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 7:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod7())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 8:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod8())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 9:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod9())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 10:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod10())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 11:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod11())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 12:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod12())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 13:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod13())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		case 14:
			rmm.method = detectionTitles[method];
			rmm.description = detectionDescriptions[method];
			if (root.checkRootMethod14())
				rmm.status = "" + failString + " " + detectionFailures[method];
			else
				rmm.status = passString;
			break;
		default:
			break;
		}
		
		
		return rmm;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspect_using_java, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class DetectRoot extends AsyncTask<String, String, ArrayList<RootMethodModel>> {
		private Context mContext;
	    public DetectRoot (Context context){
	         mContext = context;
	    }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(InspectUsingJava.this);
            progDailog.setMessage("Testing...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        @Override
        protected ArrayList<RootMethodModel> doInBackground(String... aurl) {
            //do something while spinning circling show
            return doChecking();
        }
        @Override
        protected void onPostExecute(ArrayList<RootMethodModel> list) {
            super.onPostExecute(list);
            progDailog.dismiss();
            adapter = new CustomAdapter(mContext, list.toArray(new RootMethodModel[list.size()]));
    		listView.setAdapter(adapter);
        }
    }

}
