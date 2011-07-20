package com.zylman.wwf.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.zylman.wwf.shared.Result;

@RemoteServiceRelativePath("wwfSolve")
public interface WwfSolveService extends RemoteService {
	Result getResults(String rack, String start, String contains, String end);
}
