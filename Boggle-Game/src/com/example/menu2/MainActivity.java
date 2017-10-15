package com.example.menu2;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button one,two;
	TextView display;
	char[] gameboard;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        one=(Button)findViewById(R.id.b1player);
        two=(Button)findViewById(R.id.b2player);
        one.setTextColor(0xFFFFFF00);
		two.setTextColor(0xFFFFFF00);
        display=(TextView)findViewById(R.id.Display);
        one.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				gameboard = setupBoard();
				Intent myIntent = new Intent(v.getContext(), Main5Activity.class);
				myIntent.putExtra("gameboard", gameboard);
				startActivity(myIntent);
				
			}
		});
        two.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				gameboard = setupBoard();
				Intent myIntent = new Intent(v.getContext(), Main2Activity.class);
				myIntent.putExtra("gameboard", gameboard);
				startActivity(myIntent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private char[] setupBoard() {
		char[] gb = new char[16];
		int i;
		int[] gamecube = {  11, 17, 24, 19, 19, 4,	//0
							21, 19, 7, 17, 22, 4,	//1
							4, 6, 7, 22, 13, 4,		//2
							18, 4, 14, 19, 8, 18,	//3
							0, 13, 0, 4, 4, 6,		//4
							8, 3, 18, 24, 19, 19,	//5
							14, 0, 19, 19, 14, 22,	//6
							12, 19, 14, 8, 2, 20,	//7
							0, 5, 15, 10, 5, 18,	//8
							23, 11, 3, 4, 17, 8,	//9
							7, 2, 15, 14, 0, 18,	//10
							4, 13, 18, 8, 4, 20,	//11
							24, 11, 3, 4, 21, 17,	//12
							25, 13, 17, 13, 7, 11,	//13
							13, 12, 8, 16, 7, 20,	//14
							14, 1, 1, 0, 14, 9 };	//15
		for (i=0; i<16; i++){
			gb[i] = (char)(gamecube[i*6 + (int)(6*Math.random())] + 65);
			
		}
		
		return gb;
	 }
}