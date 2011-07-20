package com.zylman.wwf.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zylman.wwf.shared.Result;

public interface WwfSolveServiceAsync {
	void getResults(
			String rack,
			String start,
			String contains,
			String end,
			AsyncCallback<Result> callback);
}
