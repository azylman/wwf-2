package com.zylman.wwf.client;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Result extends JavaScriptObject {
	protected Result() {};
	
	public final native String getWord() /*-{return this.word; }-*/;
	public final native boolean getError() /*-{return this.error; }-*/;
	public final native JsArray<SolveDataOverlay> getJsResults() /*-{return this.results; }-*/;
	
	public final ArrayList<SolveData> getResults() {
		ArrayList<SolveData> data = new ArrayList<SolveData>();
		ArrayList<SolveDataOverlay> jsData = new ArrayList<SolveDataOverlay>();
		JsArray<SolveDataOverlay> results = getJsResults();
		for(int i = 0; i < results.length(); ++i)
		{
			jsData.add(results.get(i));
		}
		for(SolveDataOverlay i : jsData)
		{
			data.add(new SolveData(i));
		}
		return data;
	}
}