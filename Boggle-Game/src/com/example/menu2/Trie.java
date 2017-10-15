package com.example.menu2;

	public class Trie {  
		char ch;  
		int cnt;
		boolean isWord;
		Trie[] child;  
		public Trie() {  
			cnt = 0;  
			child = new Trie[26];
			isWord = false;
		} 
	}