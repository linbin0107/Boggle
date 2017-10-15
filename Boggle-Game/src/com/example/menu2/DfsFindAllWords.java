package com.example.menu2;

import java.util.ArrayList;
import java.util.Vector;

public class DfsFindAllWords {
	private static char[] vexs = new char[16];
	public static int[][] gragh= new int[16][16];
	static Vector<Path> vec = new Vector<Path>();
	
	public DfsFindAllWords(){
		int i = 0, j = 0;
		for(i = 0; i < 16; i++)
		{
			vexs[i]= Main5Activity.alphvals[i];
			if(i % 4 != 3)
			{
				j = i + 1;
				labelGraph(i, j);
				j = i - 3;
				labelGraph(i, j);	
			}
			if(i % 4 != 0)
			{
				j = i - 1;
				labelGraph(i, j);
				j = i + 3;
				labelGraph(i, j);	
			}
			j = i + 4;
			labelGraph(i, j);	
			j = i - 4;
			labelGraph(i, j);	
			j = i + 5;
			labelGraph(i, j);	
			j = i - 5;
			labelGraph(i, j);	
		}
	}
	
	public ArrayList<String> traverse()
	{
		ArrayList<String> al = new ArrayList<String>();
		// Invoke DFS from each node
		int i = 0;
		for(i = 0; i < 16; i++)
		{
			Path wg = new Path(gragh);
			wg.word += vexs[i];
			vec.add(wg);
			wg.nodeNum = i;
			while(!vec.isEmpty())
			{
				String word = search();
				if(word != null && !al.contains(word))
					al.add(word);
			}
		}
		
		return al;
	}
	
	public String search()
	{
		Path wg = vec.firstElement();
		int i = wg.nodeNum;
		
		boolean getNew = false;
		for(int j = 0; j < 16; j++)
		{
			if(wg.graph[i][j] == 1)
			{
				wg.graph[i][j]++;
				Path wg1 = new Path(wg);
				wg1.word += vexs[j];
				wg1.nodeNum = j;
				// Check whether the word is a valid word
				if(Main5Activity.Dictionary.prefixSearch(TriTree.root, wg1.word))
				{
					vec.add(wg1);
					getNew = true;
					break;
				}				
			}
		}
		
		if(!getNew)
		{
			if(Main5Activity.Dictionary.search(TriTree.root, wg.word))
			{
				// Save the results
				vec.remove(0);
				if(wg.word.length()>=3){
					return wg.word;
				}
				
			}
			vec.remove(0);
		}
		return null;
	}
	
	public static void labelGraph(int i, int j)
	{
		if(j >= 0 && j < 16)
			gragh[i][j] = 1;
	}

}
