package com.zylman.wwf.server;

import java.util.Hashtable;



public final class DictWrapper
{
	private static final Dict dict = new Dict( "dict.txt" );
	
	private DictWrapper()
	{
	}
	
	public static Dict get()
	{
		return dict;
	}
	
	public static int count(String word, Character ch)
	{
		int pos = word.indexOf(ch);
		return pos == -1 ? 0 : 1 + count(word.substring(pos+1),ch);
	}
}