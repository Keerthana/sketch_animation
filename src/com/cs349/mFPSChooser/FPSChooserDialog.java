package com.cs349.mFPSChooser;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.cs349.R;

public class FPSChooserDialog extends AlertDialog implements
		OnSeekBarChangeListener {
	private int seekValue;
	private SeekBar seekBar;
	private TextView textProgress;

	public FPSChooserDialog(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_fps_chooser, null);
		setView(layout);

		// Setup and initialize FPS seeker parameters
		setTitle("Set FPS");
		seekBar = (SeekBar) layout.findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(this);
		textProgress = (TextView) layout.findViewById(R.id.fpsValue);
		textProgress.setText("");
		seekValue = 0;
		seekBar.setHapticFeedbackEnabled(true);
		seekBar.setMax(60);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		textProgress.setText(Integer.toString(progress + 10));
		seekValue = progress + 10;
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {

	}

	public int getFPS() {
		return seekValue;
	}

}
