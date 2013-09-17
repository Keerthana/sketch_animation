package com.cs349.mColorChooser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ColorPanelView extends View {

	private int mColor = 0;
	private Paint mPaint;
	private RectF mRect;

	public ColorPanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setColor(mColor);
		canvas.drawRect(mRect, mPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int w0, int h0) {
		super.onSizeChanged(w, h, w0, h0);
		mRect = new RectF(getPaddingLeft(), getPaddingTop(), w
				- getPaddingRight(), h - getPaddingBottom());
	}

	public void setColor(int color) {
		mColor = color;
		invalidate();
	}

	public int getColor() {
		return mColor;
	}
}
