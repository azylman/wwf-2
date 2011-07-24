package com.zylman.wwf.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.zylman.wwf.client.WwfWordTestService;
import com.zylman.wwf.shared.Result;

@SuppressWarnings("serial")
public class WwfWordTestServiceImpl extends RemoteServiceServlet implements WwfWordTestService {
	public Result testWord(String word) {
		Dict dict = DictWrapper.get();

		if (word != null && !word.isEmpty() && dict.isWord(word)) {
			return new Result(word).add(word, Dict.score(word));
		} else {
			return new Result().setError(true);
		}
	}
}
