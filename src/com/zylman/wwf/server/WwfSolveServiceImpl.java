package com.zylman.wwf.server;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zylman.wwf.client.WwfSolveService;
import com.zylman.wwf.shared.InputValidator;
import com.zylman.wwf.shared.Result;
import com.zylman.wwf.shared.SolveResult;

@SuppressWarnings("serial")
public class WwfSolveServiceImpl extends RemoteServiceServlet implements WwfSolveService {

	public Result findAnagrams(String rack, String start, String contains, String end) {
		ConcurrentSkipListSet<SolveResult> results = new ConcurrentSkipListSet<SolveResult>();

		if (InputValidator.validateRack(rack)
				&& InputValidator.validateOther(start)
				&& InputValidator.validateOther(contains)
				&& InputValidator.validateOther(end)) {
			Dict dict = DictWrapper.get();

			dict.solve(rack, start, contains, end, results);

			PriorityQueue<SolveResult> sortedResults = new PriorityQueue<SolveResult>(results);

			return new Result(rack).add(new LinkedList<SolveResult>(sortedResults));
		} else {
			return new Result(rack).setError(true);
		}
	}
}
