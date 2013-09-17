package com.cs349.primitives;
import java.util.ArrayList;
import java.util.List;


public class _Stroke {
	protected int strokeID;

	protected List<_Point> stroke;

	public _Stroke() {
		stroke = new ArrayList<_Point>();
	}
	
	public int getStrokeID() {
		return this.strokeID;
	}	
	
	public void setStrokeID(String strokeID) {
		this.strokeID = Integer.parseInt(strokeID);
	}
	public List<_Point> getStroke() {
		return stroke;
	}

	public void addPoint(_Point tmpPoint) {
		stroke.add(tmpPoint);
	}
}
