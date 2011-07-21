package com.zylman.wwf.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zylman.wwf.shared.Result;

@RemoteServiceRelativePath("wwfTest")
public interface WwfWordTestService extends RemoteService {
	Result testWord(String word);
}
