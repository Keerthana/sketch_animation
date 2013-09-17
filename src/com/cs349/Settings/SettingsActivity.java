package com.cs349.Settings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.cs349.R;
import com.cs349.mColorChooser.ColorChooserDialog;
import com.cs349.mFPSChooser.FPSChooserDialog;
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements
		Preference.OnPreferenceClickListener {

	private Preference mColorChooserDialog;
	private Preference mFPSDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFormat(PixelFormat.RGBA_8888);

		addPreferencesFromResource(R.xml.colorchooser);

		setUp();

	}

	private void setUp() {

		mColorChooserDialog = findPreference("color_dialog");
		mFPSDialog = findPreference("fps_dialog");

		mColorChooserDialog.setOnPreferenceClickListener(this);
		mFPSDialog.setOnPreferenceClickListener(this);

	}

	@Override
	public boolean onPreferenceClick(Preference preference) {

		final SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(SettingsActivity.this);
		String key = preference.getKey();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("color_dialog", -65794);
		editor.commit();
		if (key.equals("color_dialog")) {
			// Add Color Chooser Dialog to choose Background Color
			final ColorChooserDialog colorChooser = new ColorChooserDialog(this,
					prefs.getInt("color_dialog", 0xffffffff));

			colorChooser.setButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("color_dialog", colorChooser.getColor());
					editor.commit();
				}
			});

			colorChooser.setButton2("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			colorChooser.show();
			return true;
		} else if (key.equals("fps_dialog")) {
			
			// Add FPS Dialog to choose Frames/second
			final FPSChooserDialog fpsChooser = new FPSChooserDialog(this);

			fpsChooser.setButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("fps_dialog", fpsChooser.getFPS());
					editor.commit();
				}
			});

			fpsChooser.setButton2("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			fpsChooser.show();
			return true;
		}
		return false;
	}

}