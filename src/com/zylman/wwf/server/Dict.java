package com.zylman.wwf.server;

import com.zylman.wwf.server.SolveResult;
import com.zylman.wwf.shared.Trie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.SortedSet;

// HOW TO IMPROVE FURTHER:
//	1. Sort words before they're added to the dictionary and then turn the "words" string to a list.
//		This way, all anagrams (so all words you can make) are at the same location - e.g., dog and god
//	2. Serialize the Trie structure, and then just read it out of the static file instead of
//		needing to recompute the whole structure
public class Dict
{
	Trie wordList = new Trie();

	public Dict( String fileName )
	{
		try
		{
			String line = "non-null";
			BufferedReader fileIn = new BufferedReader( new FileReader( fileName ) );
			try
			{
				line = fileIn.readLine();
			}
			catch( IOException e )
			{
				System.out.println( "I/O Exception: " + e.getMessage() );
			}
			while( line != null )
			{
				try
				{
					wordList.addWord( line );
					line = fileIn.readLine();
				}
				catch( IOException e )
				{
					System.out.println( "I/O Exception: " + e.getMessage() );
				}
			}
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "File Not Found: " + e.getMessage() );
		}
	}
	
	protected boolean wordSearch( String word, String contains, String end, SortedSet<SolveResult> results, Trie node )
	{
		if(node == null)
		{
			return false;
		}
		
		Trie endNode = getNode(end,node);
		
		if(endNode != null && contains.isEmpty() && !endNode.word.isEmpty())
		{
			results.add(new SolveResult(endNode.word));
		}
		
		for(int i = 0; i < word.length(); i++)
		{
			Character ch = word.charAt(i);
			if(ch == '*')
			{
				String newWord = word.substring(0,i).concat(word.substring(i+1));
				
				wordSearch( newWord, contains, end, results, node.next('a') );
				wordSearch( newWord, contains, end, results, node.next('b') );
				wordSearch( newWord, contains, end, results, node.next('c') );
				wordSearch( newWord, contains, end, results, node.next('d') );
				wordSearch( newWord, contains, end, results, node.next('e') );
				wordSearch( newWord, contains, end, results, node.next('f') );
				wordSearch( newWord, contains, end, results, node.next('g') );
				wordSearch( newWord, contains, end, results, node.next('h') );
				wordSearch( newWord, contains, end, results, node.next('i') );
				wordSearch( newWord, contains, end, results, node.next('j') );
				wordSearch( newWord, contains, end, results, node.next('k') );
				wordSearch( newWord, contains, end, results, node.next('l') );
				wordSearch( newWord, contains, end, results, node.next('m') );
				wordSearch( newWord, contains, end, results, node.next('n') );
				wordSearch( newWord, contains, end, results, node.next('o') );
				wordSearch( newWord, contains, end, results, node.next('p') );
				wordSearch( newWord, contains, end, results, node.next('q') );
				wordSearch( newWord, contains, end, results, node.next('r') );
				wordSearch( newWord, contains, end, results, node.next('s') );
				wordSearch( newWord, contains, end, results, node.next('t') );
				wordSearch( newWord, contains, end, results, node.next('u') );
				wordSearch( newWord, contains, end, results, node.next('v') );
				wordSearch( newWord, contains, end, results, node.next('w') );
				wordSearch( newWord, contains, end, results, node.next('x') );
				wordSearch( newWord, contains, end, results, node.next('y') );
				wordSearch( newWord, contains, end, results, node.next('z') );
			}
			else
			{
				String newWord = word.substring(0,i).concat(word.substring(i+1));
				wordSearch( newWord, contains, end, results, node.next(ch) );
			}
		}
		
		if(!contains.isEmpty())
		{
			wordSearch( word, "", end, results, getNode(contains,node) );
		}
		
		return false;
	}

	public void solve( String word, String start, String contains, String end, SortedSet<SolveResult> results )
	{
		Trie node = getNode(start.toLowerCase(),wordList);
		
		if(node == null)
		{
			return;
		}
		
		wordSearch(word.toLowerCase(), contains.toLowerCase(), end.toLowerCase(), results, node);
	}
	
	public boolean isWord(String word)
	{
		Trie result = getNode(word.toLowerCase(),wordList);
		return result == null ? false : !result.word.isEmpty();
	}

	public Trie getNode(String word,Trie node)
	{
		if(node == null)
		{
			return null;
		}
		if(word.isEmpty())
		{
			return node;
		}
		
		return getNode(word.substring(1),node.next(word.charAt(0)));
	}
}