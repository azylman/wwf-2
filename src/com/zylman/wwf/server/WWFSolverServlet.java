package com.zylman.wwf.server;

import java.io.IOException;
import javax.servlet.http.*;

import com.zylman.wwf.shared.Result;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.PriorityQueue;

@SuppressWarnings("serial")
public class WWFSolverServlet extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String rack = req.getParameter("rack");
		String start = req.getParameter("start");
		String contains = req.getParameter("contains");
		String end = req.getParameter("end");

		if (rack != null && !rack.isEmpty() && rack.length() <= 13 && DictWrapper.count(rack, '*') < 3)
		{
			resp.getWriter().print(getResults(rack, start, contains, end));
		}
		else if (rack != null && !rack.isEmpty())
		{
			resp.getWriter().print("{\"error\":\"invalid query\"}");
		}
		else
		{
			resp.getWriter().print("{\"error\":\"no query\"}");
		}
	}

	public String getResults(String rack, String start, String contains, String end)
	{
		ConcurrentSkipListSet<Result> results = new ConcurrentSkipListSet<Result>();

		Dict dict = DictWrapper.get();

		//dict.solve(rack, start, contains, end, results);

		PriorityQueue<Result> sortedResults = new PriorityQueue<Result>(results);

		Result r = sortedResults.poll();

		StringBuilder resultText = new StringBuilder("{\"word\":\"" + rack + "\",\"error\":" + false + ",\"results\":[");

		while (r != null)
		{
			resultText.append(r.toString());
			r = sortedResults.poll();
			if (r != null) resultText.append(",");
		}

		resultText.append("]}");

		return resultText.toString();
	}

}
