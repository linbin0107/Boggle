package com.example.menu2;
  
public class TriTree {   
  
	static Trie root = new Trie(); 
    public TriTree() { 

    }

	public boolean search(Trie root, String str) { 
        Trie cur = root;  
        int idx;  
        for(int i = 0; i < str.length(); i++) {
        	char c = str.charAt(i);
        	if(c >= 'A' && c <= 'Z')
        		idx = c - 'A';
        	else if(c >= 'a' && c <= 'z')
        		idx = c - 'a';
        	else
        		return false;
            if(cur.child[idx] == null){  
                return false;  
            }  
            cur = cur.child[idx];  
        }
        if(cur.isWord)
        	return true;
        else
        	return false;
    } 

	public boolean prefixSearch(Trie root, String str){
        Trie cur = root;  
        int idx;  
        for(int i = 0; i < str.length(); i++) {
        	char c = str.charAt(i);
        	if(c >= 'A' && c <= 'Z')
        		idx = c - 'A';
        	else if(c >= 'a' && c <= 'z')
        		idx = c - 'a';
        	else
        		return false;
            if(cur.child[idx] == null){  
                return false;  
            }  
            cur = cur.child[idx];  
        }
        return true;
		
	}
	public  void insert(Trie root, String str) {

        Trie cur = root;  
        int idx;  
        for(int i = 0; i < str.length(); i++){
        	char c = str.charAt(i);
        	if(c < 'a' || c > 'z')
        		continue;
            idx = c - 'a';  
            if(cur.child[idx] == null){  
                cur.child[idx] = new Trie();  
            }  
            cur = cur.child[idx];  
            cur.ch = str.charAt(i);  
            cur.cnt = cur.cnt + 1;
        }
        cur.isWord = true;
	}  
}