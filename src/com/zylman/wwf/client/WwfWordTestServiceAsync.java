package com.zylman.wwf.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.zylman.wwf.shared.Result;


public interface WwfWordTestServiceAsync {
	void testWord(String word, AsyncCallback<Result> callback);
}
