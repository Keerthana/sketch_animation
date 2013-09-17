package com.cs349.mColorChooser;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cs349.R;
import com.cs349.mColorChooser.ColorChooserView.OnColorChangeListener;

public class ColorChooserDialog extends AlertDialog implements
		ColorChooserView.OnColorChangeListener {

	private ColorChooserView mColorChooser;
	private OnColorChangeListener mListener;
	private ColorPanelView mViewColor;

	public ColorChooserDialog(Context context, int color) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_color_chooser, null);
		setView(layout);

		// Setup and initialize Color chooser
		setTitle("Set BG Color");
		mColorChooser = (ColorChooserView) layout
				.findViewById(R.id.color_chooser_view);
		mViewColor = (ColorPanelView) layout.findViewById(R.id.new_color_panel);
		mColorChooser.setOnColorChangeListener(this);
	}

	@Override
	public void onColorChanged(int color) {
		mViewColor.setColor(color);
		if (mListener != null) {
			mListener.onColorChanged(color);
		}
	}
	
	public int getColor() {
		return mColorChooser.getColor();
	}
}
