package com.zylman.wwf.server;

import java.io.IOException;
import javax.servlet.http.*;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
public class TestWordServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String word = req.getParameter("word");
		
		Dict dict = DictWrapper.get();
		
		JsonObject out = new JsonObject();
		
		if (word != null && !word.isEmpty())
		{	

			out.addProperty("value",DictWrapper.score( dict.isWord(word) ? word : "" ));
			resp.getWriter().print(out.toString());
		}
		else
		{
			resp.getWriter().print("");
		}
	}
}
