package com.cs349.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.cs349.primitives.AnimSettings;
import com.cs349.primitives._Frame;
import com.cs349.primitives._Stroke;

public class DrawingView extends SurfaceView {

	private static float STROKE_WIDTH = 2f;
	private Paint paint = new Paint();
	private Path _path = new Path();
	private ArrayList<_Frame> animation;
	private AnimSettings animSettings;
	private float[] _data;

	public DrawingView(Context context, AttributeSet attrs,
			AnimSettings settings, ArrayList<_Frame> animation) {

		super(context);
		setWillNotDraw(false);

		this.animation = animation;
		this.animSettings = settings;
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(STROKE_WIDTH);

	}

	@Override
	public void onDraw(Canvas canvas) {
		// Clear the previous frame
		_path.reset();
		
		// Draw the current frame
		for (_Stroke _stroke : animation.get(animSettings.getFrameID())
				.getFrame()) {
			int size = _stroke.getStroke().size();
			_data = new float[2 * size];

			_data[0] = _stroke.getStroke().get(0).getPoint().x * getWidth() / 1024 ;
			_data[1] = _stroke.getStroke().get(0).getPoint().y * getHeight() / 768 ;
			
			_path.moveTo(_data[0], _data[1]);
			for (int i = 1, j = 2; i < size; ++i, j += 2) {
				_data[j] = _stroke.getStroke().get(i).getPoint().x * getWidth() / 1024 ;
				_data[j + 1] = _stroke.getStroke().get(i).getPoint().y * getHeight() / 768 ;
				_path.lineTo(_data[j], _data[j + 1]);
			}
			canvas.drawColor(animSettings.getBGColor());
			canvas.drawPath(_path, paint);
		}
	}
}
