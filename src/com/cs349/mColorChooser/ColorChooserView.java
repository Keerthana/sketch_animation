package com.cs349.mColorChooser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorChooserView extends View {

	public interface OnColorChangeListener {
		public void onColorChanged(int color);
	}

	private OnColorChangeListener mListener;
	private Paint mPaint;
	private Shader mShader;
	private RectF mColorBox;
	private Point mPoint = null;
	private float mHue, mSat, mVal;

	public ColorChooserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mHue = mSat = mVal = 360f;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int[] hue = new int[361];
		for (int i = 360, j = 0; i >= 0; i--, j++) {
			hue[j] = Color.HSVToColor(new float[] { i, 1f, 1f });
		}
		mShader = new LinearGradient(mColorBox.left, mColorBox.top,
				mColorBox.left, mColorBox.bottom, hue, null, TileMode.CLAMP);
		mPaint.setShader(mShader);
		canvas.drawRect(mColorBox, mPaint);
	}

	private float getHue(float y) {
		y -= mColorBox.top;
		return 360f - (y * 360f / mColorBox.height());
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mHue -= event.getX() * 10f;
			if (mListener != null) {
				mListener.onColorChanged(Color.HSVToColor(new float[] { mHue,
						mSat, mVal }));
			}
			invalidate();
			return true;
		}
		return super.onTrackballEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean update = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPoint = new Point((int) event.getX(), (int) event.getY());
			update = updateColor(event);
			break;
		case MotionEvent.ACTION_MOVE:
			update = updateColor(event);
			break;
		case MotionEvent.ACTION_UP:
			mPoint = null;
			update = updateColor(event);
			break;
		}
		if (update) {
			if (mListener != null) {
				mListener.onColorChanged(Color.HSVToColor(new float[] { mHue,
						mSat, mVal }));
			}
			invalidate();
			return true;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onMeasure(int w, int h) {
		setMeasuredDimension(400, 400);
	}

	@Override
	protected void onSizeChanged(int w, int h, int w0, int h0) {
		super.onSizeChanged(w, h, w0, h0);
		mColorBox = new RectF(0, 0, w, h);
	}
	
	private boolean updateColor(MotionEvent event) {
		if (mPoint == null)
			return false;
		if (mColorBox.contains(mPoint.x, mPoint.y)) {
			mHue = getHue(event.getY());
			return true;
		}
		return false;
	}

	public void setOnColorChangeListener(OnColorChangeListener listener) {
		mListener = listener;
	}

	public int getColor() {
		return Color.HSVToColor(new float[] { mHue, mSat, mVal });
	}
}