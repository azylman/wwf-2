package com.zylman.wwf.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Result implements IsSerializable {
	private String query;
	private List<SolveResult> results;
	
	public Result(String query) {
		this.query = query;
	}
	
	public Result() {};

	public void add(String word, int score) {
		results.add(new SolveResult(word, score));
	}
	
	public Result add(List<SolveResult> results) {
		this.results = results;
		return this;
	}
	
	public String getQuery() {
		return query;
	}
	
	public List<SolveResult> getWords() {
		return results;
	}
}