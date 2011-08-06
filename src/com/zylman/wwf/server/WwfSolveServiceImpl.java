package com.zylman.wwf.server;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zylman.wwf.client.WwfSolveService;
import com.zylman.wwf.shared.InputValidator;
import com.zylman.wwf.shared.Result;
import com.zylman.wwf.shared.SolveResult;

@SuppressWarnings("serial")
public class WwfSolveServiceImpl extends RemoteServiceServlet implements WwfSolveService {

	public Result findAnagrams(String rack, String start, String contains, String end) {
		SolveResultList results = new SolveResultList();

		if (InputValidator.validateRack(rack)
				&& InputValidator.validateOther(start)
				&& InputValidator.validateOther(contains)
				&& InputValidator.validateOther(end)) {
			Dict dict = DictWrapper.get();

			dict.solve(rack, start, contains, end, results);
			
			Comparator<SolveResult> solveSorter = new Comparator<SolveResult>() {
				@Override public int compare(SolveResult r1, SolveResult r2) {
					if (r1.getScore() != r2.getScore()) {
						return r1.getScore() > r2.getScore() ? -1 : 1;
					}
					
					if (r1.getLength() != r2.getLength()) {
						return r1.getLength() > r2.getLength() ? -1 : 1;  
					}
					
					return -r1.getWord().compareTo(r2.getWord());
				}
			};
			
			List<SolveResult> sortedResults = new LinkedList<SolveResult>(results);
			Collections.sort(sortedResults, solveSorter);
			
			return new Result(rack).add(sortedResults);
		} else {
			return new Result(rack).setError(true);
		}
	}
}
