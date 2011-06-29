package com.zylman.wwf.server;

import com.google.gson.JsonObject;

public class SolveResult implements Comparable<SolveResult>
{
	private String result;
	private int score;
	
	public SolveResult(String word)
	{
		result = word;
		score = DictWrapper.score(word);
	}
	
	public JsonObject toJSON()
	{
		JsonObject temp = new JsonObject();
		temp.addProperty("word",result);
		temp.addProperty("score",score);
		temp.addProperty("length",result.length());
		return temp;
	}
	
	public String toString()
	{
		return toJSON().toString();
	}
	
	int getScore()
	{
		return score;
	}
	
	String getResult()
	{
		return result;
	}
	
	//Actually compares opposite of expected, because we need our priority queue to be reversed
	public int compareTo(SolveResult r)
	{		
		if(score != r.getScore())
		{
			return (score < r.getScore()) ? 1 : -1;
		}
		
		if(result.length() != r.getResult().length())
		{
			return (result.length() < r.getResult().length()) ? 1 : -1;
		}
		
		return -result.compareTo(r.getResult());
	}
}