package com.zylman.wwf.server;

import java.util.concurrent.ConcurrentSkipListSet;

import com.zylman.wwf.shared.SolveResult;

@SuppressWarnings("serial")
public class SolveResultList extends ConcurrentSkipListSet<SolveResult> {
	@Override public boolean add(SolveResult r1) {
		if (contains(r1)) {
			SolveResult r2 = ceiling(r1);
			super.add(r1.getScore() > r2.getScore() ? r1 : r2);
			return true;
		} else {
			super.add(r1);
		}
		
		return false;
	}
}
