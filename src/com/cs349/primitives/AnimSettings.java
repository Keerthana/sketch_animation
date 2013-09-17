package com.cs349.primitives;

public class AnimSettings {
	int bgcolor;
	int fps;	
	int frameID;

	public AnimSettings() {
		this.bgcolor = -65794; // White
		this.fps = 30;
		this.frameID = 0;
	}

	public void setBGColor(int bgcolor) {
		this.bgcolor = bgcolor;
	}

	public int getBGColor() {
		return this.bgcolor;
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
	}

	public int getFPS() {
		return this.fps;
	}	
	
	public void setFrameID(int frameID) {
		this.frameID = frameID;
	}

	public int getFrameID() {
		return this.frameID;
	}

}
