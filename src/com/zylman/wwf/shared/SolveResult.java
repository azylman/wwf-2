package com.zylman.wwf.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SolveResult implements Comparable<SolveResult>, IsSerializable {
	private String word;
	private Integer score;
	private Integer length;
	
	public SolveResult(String word, int score) {
		this.word = word;
		this.score = score;
		this.length = word.length();
	}
	
	public SolveResult() {}
	
	public String getWord() {
		return word;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public Integer getLength() {
		return length;
	}
	
	// Actually compares opposite of expected, because we need our priority
	// queue to be reversed
	public int compareTo(SolveResult r) {
		if (getScore() != r.getScore()) {
			return (getScore() < r.getScore()) ? 1 : -1;
		}

		if (getLength() != r.getLength()) {
			return (getLength() < r.getLength()) ? 1 : -1;
		}

		return -getWord().compareTo(r.getWord());
	}
}
