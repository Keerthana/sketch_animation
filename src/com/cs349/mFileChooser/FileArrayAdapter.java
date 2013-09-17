package com.cs349.mFileChooser;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cs349.R;

public class FileArrayAdapter extends ArrayAdapter<Option> {

	private Context c;
	private int id;
	private List<Option> items;

	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Option> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}

	public Option getItem(int i) {
		return items.get(i);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(id, null);
		}
		final Option o = items.get(position);
		if (o != null) {
			TextView parentDir = (TextView) v.findViewById(R.id.parentDir);
			TextView fileName = (TextView) v.findViewById(R.id.fileName);

			if (parentDir != null)
				parentDir.setText(o.getName());
			if (fileName != null)
				fileName.setText(o.getData());

		}
		return v;
	}

}
