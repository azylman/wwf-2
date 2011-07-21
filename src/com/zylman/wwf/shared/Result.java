package com.zylman.wwf.shared;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Result implements IsSerializable {
	private String query;
	private List<SolveResult> results = new LinkedList<SolveResult>();
	
	private boolean error = false;
	public Result(String query) {
		this.query = query;
	}
	
	public Result() {};

	public Result add(String word, int score) {
		results.add(new SolveResult(word, score));
		return this;
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
	
	public boolean getError() {
		return error;
	}
	
	public Result setError(boolean error) {
		this.error = error;
		return this;
	}
}