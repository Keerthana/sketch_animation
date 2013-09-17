package com.cs349.a5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cs349.R;
import com.cs349.mFileChooser.FileChooserActivity;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doFileChoose(View v) {
		Intent i = new Intent(MainActivity.this, FileChooserActivity.class);
		startActivity(i);
	}
}