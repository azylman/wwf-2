package com.zylman.wwf.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zylman.wwf.client.WwfWordTestService;
import com.zylman.wwf.shared.Result;


public class WwfWordTestServiceImpl extends RemoteServiceServlet implements WwfWordTestService {
	public Result testWord(String word) {
		Dict dict = DictWrapper.get();

		if (word != null && !word.isEmpty() && dict.isWord(word)) {
			Result r = new Result(word);
			r.add(word, Dict.score(word));
			r.setError(false);
			return r;
		} else {
			Result r = new Result();
			r.setError(true);
			return r;
		}
	}
}
