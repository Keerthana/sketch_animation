package com.cs349.primitives;

import java.util.ArrayList;
import java.util.List;

public class _Frame {
	protected int frameID;
	protected List<_Stroke> frame;

	public _Frame() {
		frame = new ArrayList<_Stroke>();
	}

	public int getFrameID() {
		return this.frameID;
	}

	public void setFrameID(String frameID) {
		this.frameID = Integer.parseInt(frameID);
	}

	public List<_Stroke> getFrame() {
		return this.frame;
	}

	public void addStroke(_Stroke stroke) {
		frame.add(stroke);
	}

	public void addStroke(String value, _Stroke stroke) {
		frame.add(Integer.parseInt(value), stroke);
	}

}
