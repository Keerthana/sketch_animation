package com.cs349.mFileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import com.cs349.R;
import com.cs349.mXMLParser.ParseActivity;

public class FileChooserActivity extends ListActivity {

	private File currentDir;
	private FileArrayAdapter adapter;
	private Stack<File> dirStack;
	String baseDir = Environment.getExternalStorageDirectory().getPath();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentDir = new File(baseDir);
		dirStack = new Stack<File>();
		fill(currentDir);
	}

	private void fill(File f) {
		File[] dirs = f.listFiles();
		double fileSize;
		String fileExt;
		this.setTitle("Current Dir: " + f.getName());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		try {
			for (File ff : dirs) {
				if (ff.isDirectory())
					dir.add(new Option(ff.getName(), "Folder", ff
							.getAbsolutePath()));
				else {
					fileExt = "";
					if (ff.length() > 1024 * 1024) {
						fileSize = ff.length() / (1024 * 1024);
						fileExt += "M";
					} else if (ff.length() > 1024) {
						fileSize = ff.length() / (1024);
						fileExt += "K";
					} else
						fileSize = ff.length();
					fileExt += "b";
					fls.add(new Option(ff.getName(), "File Size: " + fileSize
							+ fileExt, ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase(baseDir))
			dir.add(0, new Option("..", "Parent Directory", f.getParent()));
		adapter = new FileArrayAdapter(FileChooserActivity.this,
				R.layout.activity_file_chooser, dir);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if (o.getData().equalsIgnoreCase("folder")) {
			dirStack.push(currentDir);
			currentDir = new File(o.getPath());
			fill(currentDir);
		} else if (o.getData().equalsIgnoreCase("parent directory")) {
			currentDir = dirStack.pop();
			fill(currentDir);
		} else {
			onFileClick(o);
		}

	}

	private void onFileClick(Option o) {
		Intent i = new Intent(FileChooserActivity.this, ParseActivity.class);
		i.putExtra("currentPath", o.getPath());
		i.putExtra("currentFile", o.getName());
		startActivity(i);
	}

}