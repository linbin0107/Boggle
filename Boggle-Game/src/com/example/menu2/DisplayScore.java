package com.example.menu2;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayScore extends Activity {
	public TextView score_tv1;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_score);
        Intent intent = getIntent();
		ArrayList<String> wordsFound = intent.getStringArrayListExtra("word_found");
		ArrayList<String> allPossibleWords = intent.getStringArrayListExtra("all_words");
		int score = intent.getIntExtra("score", 0);
        String wordsFound_str = new String();
        String allPossibleWords_str = new String();
		
		for (String word : wordsFound) {
			wordsFound_str += word + "\n";
		}
		for (String word : allPossibleWords) {
			allPossibleWords_str += word + "\n";
		}
		
		TextView wordsFound_tv = (TextView)findViewById(R.id.wordsFound);
		wordsFound_tv.setText(wordsFound_str);
		
		TextView allPossibleWords_tv = (TextView)findViewById(R.id.allPossibleWords);
		allPossibleWords_tv.setText(allPossibleWords_str);
		
		TextView score_tv = (TextView)findViewById(R.id.score);
		score_tv.setText(score + "");
		
    
    }
    
}
