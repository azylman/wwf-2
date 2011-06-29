package com.zylman.wwf.server;

import java.util.Hashtable;

public final class DictWrapper
{
	private static final Dict dict = new Dict( "dict.txt" );
	
	@SuppressWarnings("serial")
	private static final Hashtable<Character,Integer> scores = new Hashtable<Character,Integer>() {{
		put('a', 1);
		put('b', 4);
		put('c', 4);
		put('d', 2);
		put('e', 1);
		put('f', 4);
		put('g', 3);
		put('h', 3);
		put('i', 1);
		put('j', 10);
		put('k', 5);
		put('l', 2);
		put('m', 4);
		put('n', 2);
		put('o', 1);
		put('p', 4);
		put('q', 5);
		put('r', 1);
		put('s', 1);
		put('t', 1);
		put('u', 2);
		put('v', 5);
		put('w', 4);
		put('x', 8);
		put('y', 3);
		put('z', 10);
	}};
	
	private DictWrapper()
	{
	}
	
	public static Dict get()
	{
		return dict;
	}
	
	public static int score(String word)
	{
		int sum = 0;
		for(int i = 0; i < word.length(); ++i)
		{
			sum += scores.get(word.toLowerCase().charAt(i));
		}
		return sum;
	}
	
	public static int count(String word, Character ch)
	{
		int pos = word.indexOf(ch);
		return pos == -1 ? 0 : 1 + count(word.substring(pos+1),ch);
	}
}