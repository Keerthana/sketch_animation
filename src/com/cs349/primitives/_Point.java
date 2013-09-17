package com.cs349.primitives;

import android.graphics.Point;

public class _Point {

	protected int pointID;
	protected Point point;

	public _Point() {
		point = new Point();
	}

	public int getPointID() {
		return this.pointID;
	}

	public void setPointID(String pointID) {
		this.pointID = Integer.parseInt(pointID);
	}

	public Point getPoint() {
		return this.point;
	}

	public void setX(String x) {
		point.x = Integer.parseInt(x);
	}

	public void setY(String y) {
		point.y = Integer.parseInt(y);
	}
}
