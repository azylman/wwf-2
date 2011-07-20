package com.zylman.wwf.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SolveDataOverlay extends JavaScriptObject {
	protected SolveDataOverlay() {};
	
	// JSNI methods to get stock data.
	  public final native String getWord() /*-{ return this.word; }-*/;
	  public final native String getScore() /*-{ return this.score; }-*/;
	  public final native String getLength() /*-{ return this.length; }-*/;
}