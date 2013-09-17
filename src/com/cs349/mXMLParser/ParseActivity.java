package com.cs349.mXMLParser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

import com.cs349.R;
import com.cs349.Settings.SettingsActivity;
import com.cs349.primitives.AnimSettings;
import com.cs349.primitives._Frame;
import com.cs349.views.DrawingView;

public class ParseActivity extends Activity {
	static ArrayList<_Frame> animation;
	static AnimSettings animSettings;
	private static final int resultCode = 1;
	
	private ProgressDialog pDialog;
	private LinearLayout layout;
	private ToggleButton playButton;
	private SeekBar playSeeker;
	private int progress, time = 0;
	
	
	boolean play = false;

	Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canvas);
		animSettings = new AnimSettings();

		Bundle bundle = getIntent().getExtras();
		this.setTitle(bundle.getString("currentFile"));
		new AsyncData().execute(bundle.getString("currentPath"));
	}

	class AsyncData extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(ParseActivity.this);
			pDialog.setTitle("Loading....");
			pDialog.setMessage("Please wait...");
			pDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// animation = new DOMParser().getData(params[0]);
			animation = new SAXParserImpl().getData(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			// Debug
			// new SAXParserImpl().printData(animation);
			layout = (LinearLayout) findViewById(R.id.linearLayout1);

			playSeeker = (SeekBar) findViewById(R.id.playSeeker);
			playSeeker.setMax(animation.size());

			playSeeker
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							animSettings.setFrameID(progress);
							time = progress;
//							playButton.toggle();

							layout.addView(new DrawingView(ParseActivity.this,
									null, animSettings, animation));
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
							if (play) {
								timer.cancel();
								playButton.toggle();
								play = false;
							}
						}

						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
						}
					});

			// attach an OnClickListener
			playButton = (ToggleButton) findViewById(R.id.playButton);
			playButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int rate = 1000/ 30;
					int fps = animSettings.getFPS();
					if ( fps!= 0)
						rate = 1000/fps;
					
					if (playButton.isChecked()) {
						// Set the schedule function and rate
						timer = new Timer();
						timer.scheduleAtFixedRate(new TimerTask() {
							@Override
							public void run() {
								if (time < animation.size()) {
									animSettings.setFrameID(time);
									playSeeker.setProgress(time);
									time += 1;
									play = true;
								}
							}
						}, 0, rate);
					} else {
						time -= 1;
						timer.cancel();
						play = false;
					}
				}
			});
			// layout = (LinearLayout) findViewById(R.id.linearLayout1);
			layout.addView(new DrawingView(ParseActivity.this, null,
					animSettings, animation));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		timer.cancel();
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, resultCode);
			break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		timer.cancel();
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data) {
		super.onActivityResult(request, result, data);

		switch (request) {
		case resultCode:
			SharedPreferences sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			animSettings.setBGColor(sharedPrefs.getInt("color_dialog", 0));
			animSettings.setFPS(sharedPrefs.getInt("fps_dialog", 0));
			layout = (LinearLayout) findViewById(R.id.linearLayout1);
			layout.addView(new DrawingView(ParseActivity.this, null,
					animSettings, animation));
			break;

		}
	}
}
