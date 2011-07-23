package com.zylman.wwf.server;

import java.util.LinkedHashMap;
import java.util.Iterator;

public class Trie {
	protected LinkedHashMap<Character, Trie> children = new LinkedHashMap<Character, Trie>();
	public String word = "";

	public int size = 0;

	public Trie() {
	}

	public Trie(Trie oldTrie) {
		word = oldTrie.word;
		// children = (LinkedHashMap< Character, Trie >)oldTrie.children.clone();
		children = new LinkedHashMap<Character, Trie>(oldTrie.children);
	}

	public Trie clone() {
		Trie newTrie = new Trie();
		newTrie.word = word;

		// Iterator it = children.keySet().iterator();
		Iterator<Character> it = children.keySet().iterator();
		while (it.hasNext()) {
			Character key = (Character) it.next();
			newTrie.children.put(key, children.get(key).clone());
		}

		return newTrie;
	}

	public Trie next(char ch) {
		return children.containsKey(ch) ? children.get(ch) : null;
	}

	public Trie addBranch(char ch) {
		if (next(ch) == null) {
			children.put(ch, new Trie());
		}
		return children.get(ch);
	}

	public void addWord(String word) {
		Trie temp = this;
		++size;

		for (int i = 0; i < word.length(); i++) {
			temp = temp.addBranch(word.charAt(i));
		}

		temp.word = word;
	}
}
