package com.zylman.wwf.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.SortedSet;

import com.zylman.wwf.shared.SolveResult;

public class Dict {
	private Trie wordList = new Trie();
	
	@SuppressWarnings("serial")
	private static final Hashtable<Character, Integer> scores = new Hashtable<Character, Integer>() {
		{
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
		}
	};

	public static int score(String word) {
		int sum = 0;
		for (int i = 0; i < word.length(); ++i) {
			sum += scores.get(word.toLowerCase().charAt(i));
		}
		return sum;
	}

	public Dict(String fileName) {
		try {
			String line = "non-null";
			BufferedReader fileIn = new BufferedReader(new FileReader(fileName));
			try {
				line = fileIn.readLine();
			} catch (IOException e) {
				System.out.println("I/O Exception: " + e.getMessage());
			}
			while (line != null) {
				try {
					wordList.addWord(line);
					line = fileIn.readLine();
				} catch (IOException e) {
					System.out.println("I/O Exception: " + e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found: " + e.getMessage());
		}
	}

	protected int wordSearch(String word, String contains, String end, int numWildcards,
			SortedSet<SolveResult> results, Trie node) {
		if (node == null) {
			return 0;
		}

		Trie endNode = getNode(end, node);
		
		if (endNode != null && contains.isEmpty() && !endNode.getWord().isEmpty()) {
			results.add(new SolveResult(endNode.getWord(), score(endNode.getWord())));
		}
		
		int count = 0;
		
		if (numWildcards > 0) {
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('a'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('b'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('c'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('d'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('e'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('f'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('g'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('h'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('i'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('j'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('k'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('l'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('m'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('n'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('o'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('p'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('q'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('r'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('s'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('t'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('u'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('v'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('w'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('x'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('y'));
			count += wordSearch(word, contains, end, numWildcards - 1, results, node.next('z'));
		}

		for (int i = 0; i < word.length(); i++) {
			Character ch = word.charAt(i);
			String newWord = word.substring(0, i).concat(word.substring(i + 1));
			count += wordSearch(newWord, contains, end, numWildcards, results, node.next(ch));
		}

		if (!contains.isEmpty()) {
			count += wordSearch(word, "", end, numWildcards, results, getNode(contains, node));
		}

		return count + 1;
	}

	public void solve(String word, String start, String contains, String end,
			SortedSet<SolveResult> results) {
		Trie node = getNode(start.toLowerCase(), wordList);
		
		int numWildcards = removeWildcards(word);
		
		if (node == null) {
			return;
		}

		wordSearch(
				word.toLowerCase(),
				contains.toLowerCase(),
				end.toLowerCase(),
				numWildcards,
				results,
				node);
	}

	public boolean isWord(String word) {
		Trie result = getNode(word.toLowerCase(), wordList);
		return result == null ? false : !result.getWord().isEmpty();
	}

	public Trie getNode(String word, Trie node) {
		if (node == null) {
			return null;
		}
		if (word.isEmpty()) {
			return node;
		}

		return getNode(word.substring(1), node.next(word.charAt(0)));
	}
	
	private int removeWildcards(String word) {
		if (word.contains("*")) {
			int wildcardIndex = word.indexOf("*");
			word = word.substring(0, wildcardIndex).concat(word.substring(wildcardIndex + 1));
			return 1 + removeWildcards(word);
		} else {
			return 0;
		}
	}
}