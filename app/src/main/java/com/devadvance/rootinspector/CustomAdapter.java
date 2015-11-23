package com.devadvance.rootinspector;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<RootMethodModel> {
	private final Context context;
	private final RootMethodModel[] values;

	public CustomAdapter(Context context, int resource, RootMethodModel[] values) {
		super(context, resource, values);
		this.context = context;
		this.values = values;
		// TODO Auto-generated constructor stub
	}

	public CustomAdapter(Context context, RootMethodModel[] values) {
		this(context, R.layout.row_layout, values);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_layout, parent, false);
		TextView titleText = (TextView) rowView.findViewById(R.id.title);
		TextView descriptionText = (TextView) rowView.findViewById(R.id.description);
		TextView statusText = (TextView) rowView.findViewById(R.id.status);

		titleText.setText(values[position].method);
		descriptionText.setText(values[position].description);
		statusText.setText(values[position].status);
		if (values[position].status.startsWith("Failed")) {
			statusText.setTextColor(Color.parseColor("#B00C00"));
		} else {
			statusText.setTextColor(Color.parseColor("#1FA600"));
		}
		
		return rowView;
	}

}
