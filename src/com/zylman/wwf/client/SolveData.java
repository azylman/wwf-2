package com.zylman.wwf.client;

import com.zylman.wwf.client.SolveDataOverlay;

public class SolveData {
	public String word;
	public String score;
	public String length;
	
	public SolveData(String word, String score, String length)
	{
		this.word = word;
		this.score = score;
		this.length = length;
	}
	
	public SolveData(SolveDataOverlay jsObject)
	{
		this.word = jsObject.getWord();
		this.score = jsObject.getScore();
		this.length = jsObject.getLength();
	}
}