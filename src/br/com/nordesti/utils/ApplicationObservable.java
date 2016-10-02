package br.com.nordesti.utils;

import java.util.Observable;

public class ApplicationObservable extends Observable {

	private Number appHeight;
	private Number appWidth;
	
	public Number getAppHeight() {
		return appHeight;
	}

	public void setAppHeight(Number appHeight) {
		this.appHeight = appHeight;
		setChanged();
		notifyObservers();
	}

	public Number getAppWidth() {
		return appWidth;
	}

	public void setAppWidth(Number appWidth) {
		this.appWidth = appWidth;
		setChanged();
		notifyObservers();
	}

	public ApplicationObservable(Number appHeight,  Number appWidth) {
		setAppHeight(appHeight);
		setAppWidth(appWidth);
	}

}
