package com.example.menu2;

public class Path {
	public int[][] graph = new int[16][16];
	
	public boolean isNew = false;
	
	public String word = "";
	
	int nodeNum;

	public Path() {
		// TODO Auto-generated constructor stub
	}

	public Path(Path wg) {
		// TODO Auto-generated constructor stub
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				this.graph[i][j] = wg.graph[i][j];
			}
		}
		
		word = wg.word;
		
		isNew = true;
		
		nodeNum = wg.nodeNum;
	}

	public Path(int[][] g) {
		// TODO Auto-generated constructor stub
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				this.graph[i][j] = g[i][j];
			}
		}
		
		isNew = true;
	}

}
