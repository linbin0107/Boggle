package com.example.menu2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.util.EncodingUtils;

//import com.example.menu2.ImageAdapter.pos;

public class Main5Activity extends Activity implements OnGestureListener {
	
	private GestureDetector gDetector;
	public String str = "";
    public int score = 0;
    public TextView wordAttempt_tv;
    public TextView score_tv;
    public TextView wordsFound_tv;
    
    public String[] wordsFound = new String[100];
    public int wordsFoundCount = 0;
    public ArrayList<String> list1= new ArrayList<String>();
    public ArrayList<String> allWordsList= new ArrayList<String>();
    
    private boolean [] adjacent_buttons = new boolean[16];
    private boolean [] unused_buttons   = new boolean[16];
    private boolean [] valid_buttons    = new boolean[16];
    
    //private boolean pos0_set = false;
    //private int i;
    private final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("m:ss");
	CountDownTimer startWait;
	long seconds = 100000;
	public static final String ENCODING = "UTF-8"; 
	public static TriTree Dictionary = null;
	private DfsFindAllWords Solver = null;
	public static char[] alphvals;
	private int[] button_imgs = { 
			R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g,
			R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k,
			R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o,
			R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s,
			R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.w,
			R.drawable.x, R.drawable.y, R.drawable.z, 
			R.drawable.a_1, R.drawable.b_1, R.drawable.c_1,
			R.drawable.d_1, R.drawable.e_1, R.drawable.f_1, R.drawable.g_1,
			R.drawable.h_1, R.drawable.i_1, R.drawable.j_1, R.drawable.k_1,
			R.drawable.l_1, R.drawable.m_1, R.drawable.n_1, R.drawable.o_1,
			R.drawable.p_1, R.drawable.q_1, R.drawable.r_1, R.drawable.s_1,
			R.drawable.t_1, R.drawable.u_1, R.drawable.v_1, R.drawable.w_1,
			R.drawable.x_1, R.drawable.y_1, R.drawable.z_1, 
			R.drawable.submit };
	
	public Integer[] imageViewUsed = {
			R.id.imageView0, R.id.imageView1, R.id.imageView2, R.id.imageView3, 
			R.id.imageView4, R.id.imageView5, R.id.imageView6, R.id.imageView7, 
			R.id.imageView8, R.id.imageView9, R.id.imageView10, R.id.imageView11, 
			R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15
	};
	
	public static char getAlphVal(int i) {
		return alphvals[i];
	}
	
	public pos[] positions = new pos[] {
			new pos(new int[] { 11, 17, 24, 19, 19, 4 }),
			new pos(new int[] { 21, 19, 7, 17, 22, 4 }),
			new pos(new int[] { 4, 6, 7, 22, 13, 4 }),
			new pos(new int[] { 18, 4, 14, 19, 8, 18 }),
			new pos(new int[] { 0, 13, 0, 4, 4, 6 }),
			new pos(new int[] { 8, 3, 18, 24, 19, 19 }),
			new pos(new int[] { 14, 0, 19, 19, 14, 22 }),
			new pos(new int[] { 12, 19, 14, 8, 2, 20 }),
			new pos(new int[] { 0, 5, 15, 10, 5, 18 }),
			new pos(new int[] { 23, 11, 3, 4, 17, 8 }),
			new pos(new int[] { 7, 2, 15, 14, 0, 18 }),
			new pos(new int[] { 4, 13, 18, 8, 4, 20 }),
			new pos(new int[] { 24, 11, 3, 4, 21, 17 }),
			new pos(new int[] { 25, 13, 17, 13, 7, 11 }),
			new pos(new int[] { 13, 12, 8, 16, 7, 20 }),
			new pos(new int[] { 14, 1, 1, 0, 14, 9 }),
	};

	private class pos {
		int[] values;
		public Random rn = new Random();

		public pos(int[] values) {
			this.values = values;
		}

		public int getRandomInt() {
			return values[rn.nextInt(values.length)];
		}
	}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        gDetector = new GestureDetector(this);
        wordAttempt_tv = (TextView) findViewById(R.id.textView1);
        score_tv = (TextView) findViewById(R.id.textView3);
        wordsFound_tv = (TextView) findViewById(R.id.textView5);
        TextView timer = (TextView)findViewById(R.id.textView4);
        timer.setTextColor(Color.BLUE);
		timer.setText(String.valueOf(timerFormat.format(new java.util.Date(seconds))));
		Intent intent = getIntent();
		alphvals = intent.getCharArrayExtra("gameboard");
		for (int a=0; a<alphvals.length; a++) {
			System.out.println("alphvals["+a+"]=" + (char)(alphvals[a]));
		}
		resetButtons();
        
		Dictionary = new TriTree();
    	String FilePath = "Dictionary.txt";
        try { 
                InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(FilePath) ); 
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line= bufReader.readLine();

                while(line != null){
                	Dictionary.insert(TriTree.root,line);
                	line = bufReader.readLine();
                }
            } catch (Exception e) { 
                e.printStackTrace(); 
            }
		
		Solver = new DfsFindAllWords();
        allWordsList = Solver.traverse();
        setupTimers();
       
        //ImageView checkbox = (ImageView) findViewById(R.id.imageView16);
        
        /*checkbox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if (Dictionary.search(TriTree.root, str)){
            		if(!list1.contains(str)){
            			calcScore(str.length());
            			if(str.length()>=3){
            				list1.add(str);
            			}
            			printMe(list1);}
            	}
        		str="";
        		wordAttempt_tv.setText("");
        		resetButtons();
            }
        });*/
 
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {return gDetector.onTouchEvent(me);}

	public boolean onDown(MotionEvent arg1) {
		if (str.length()==0) {
			if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
				addToString(0);	
			}
			if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
				addToString(1);	
			}
			if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
				addToString(2);	
			}
			if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
				addToString(3);	
			}
			
			//Row 2//
			if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
				addToString(4);	
			}
			if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
				addToString(5);	
			}
			if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
				addToString(6);	
			}
			if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
				addToString(7);	
			}

			//Row 3//
			if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
				addToString(8);	
			}
			if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
				addToString(9);	
			}
			if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
				addToString(10);	
			}
			if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
				addToString(11);	
			}

			//Row 4//
			if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
				addToString(12);	
			}
			if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
				addToString(13);	
			}
			if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
				addToString(14);	
			}
			if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
				addToString(15);	
			}
			
		}
		return true;
	}

	public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {return false;}

	public void onLongPress(MotionEvent arg0) {}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		System.out.println(arg1.getRawY() + "/" + arg1.getRawX());
		
		//Row 1//
		if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
			addToString(0);	
		}
		if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
			addToString(1);	
		}
		if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
			addToString(2);	
		}
		if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>158 && arg1.getRawY()<202) ) {
			addToString(3);	
		}
		
		//Row 2//
		if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
			addToString(4);	
		}
		if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
			addToString(5);	
		}
		if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
			addToString(6);	
		}
		if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>247 && arg1.getRawY()<291) ) {
			addToString(7);	
		}

		//Row 3//
		if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
			addToString(8);	
		}
		if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
			addToString(9);	
		}
		if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
			addToString(10);	
		}
		if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>336 && arg1.getRawY()<380) ) {
			addToString(11);	
		}

		//Row 4//
		if ((arg1.getRawX()>85 && arg1.getRawX()<127) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
			addToString(12);	
		}
		if ((arg1.getRawX()>173 && arg1.getRawX()<215) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
			addToString(13);	
		}
		if ((arg1.getRawX()>261 && arg1.getRawX()<303) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
			addToString(14);	
		}
		if ((arg1.getRawX()>349 && arg1.getRawX()<391) && (arg1.getRawY()>425 && arg1.getRawY()<469) ) {
			addToString(15);	
		}
			return true;
	}

	public void onShowPress(MotionEvent arg0) {}

	public boolean onSingleTapUp(MotionEvent arg0) {
		if (Dictionary.search(TriTree.root, str)){
			if(!list1.contains(str)){
				calcScore(str.length());
				if(str.length()>=3){
					list1.add(str);
					wordsFound_tv.setText(str + "   " + wordsFound_tv.getText());
					wordsFoundCount++;
					if (wordsFoundCount==8){
						wordsFound_tv.setTextSize(20);
					}
					else if (wordsFoundCount==16) {
						wordsFound_tv.setTextSize(14);
					}
					else if (wordsFoundCount==20) {
						wordsFound_tv.setTextSize(10);
					}
				}
			printMe(list1);}
		}
		str="";
		wordAttempt_tv.setText("");
		resetButtons();
		return true;
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_main5, menu);
	        return true;
	    }
	  	 
	 /*private void setupBoard(char[] board) {
		 for (int p=0;p<16;p++) {
			//imageViewUsed[p] = button_imgs[board[p]-65];
			 ImageView iv = (ImageView) findViewById(imageViewUsed[p]);
			//alphvals[p] = (char) (board[p] + 65);
			iv.setImageResource(button_imgs[(board[p])-65]);
			System.out.println("position " + p + ": " + board[p] + " -- " + alphvals[p]);
			System.out.println("imageViewUsed["+p+"]: "+imageViewUsed[p]);
			 
		 }
	 }*/
	 
/*	 private int[] setupBoard() {
		 int[] board = new int[16];
		 for (int p=0;p<16;p++) {
			//ImageView pos0 = (ImageView) findViewById(R.id.imageView0);
			//pos0.setImageResource(R.drawable.m);
			if (p==0) {
				ImageView pos0 = (ImageView) findViewById(imageViewUsed[p]);
				if (pos0_set == false) {
					i = positions[p].getRandomInt();
					alphvals[p] = (char) (i + 65);
					pos0.setImageResource(button_imgs[i]);
					board[p]=button_imgs[i];
					System.out.println("position " + p + ": " + (char) (i + 65) + " -- " + alphvals[p]);
					pos0_set = true;
				}
			} 
			else {
				ImageView pos0 = (ImageView) findViewById(imageViewUsed[p]);
				i = positions[p].getRandomInt();
				alphvals[p] = (char) (i + 65);
				pos0.setImageResource(button_imgs[i]);
				board[p]=button_imgs[i];
				System.out.println("position " + p + ": " + (char) (i + 65) + " -- " + alphvals[p]);
			}
		 }
		 for (int i=0; i<16; i++) {
			 System.out.println(board[i]);
		 }
		 return board;
	 }
*/	 
	 private void addToString(int pos){
		 if (isValidButtonPushed(pos)==true) {
			 char value = this.getAlphVal(pos);
			 str += value;
			 wordAttempt_tv.setText(str);
			 ImageView pos0 = (ImageView) findViewById(imageViewUsed[pos]);
			 pos0.setImageResource(button_imgs[value-65+26]);
			 setPushedButton(pos);
			 setAdjacentButtons(pos);
			 setValidButtons(adjacent_buttons, unused_buttons);
		 }
		 
	 }
	 
	 public void setupTimers()
	 {
		startWait = new CountDownTimer(seconds, 1000) {
			public void onTick(long millisUntilFinished) {
				TextView text = (TextView)findViewById(R.id.textView4);
				text.setText(String.valueOf(timerFormat.format(new java.util.Date(millisUntilFinished)))); 			
			}
	
			public void onFinish() {
//				TextView text = (TextView)findViewById(R.id.textView1);
				TextView text = (TextView)findViewById(R.id.textView4);
				Intent openStartingPoint= new Intent("com.example.menu2.DISPLAYSCORE");
				openStartingPoint.putExtra("score",score);
				openStartingPoint.putExtra("word_found", list1);
				openStartingPoint.putExtra("all_words", allWordsList);
				startActivity(openStartingPoint);
				text.setText(String.valueOf(timerFormat.format(new java.util.Date(0)))); 
			}
		};
		startWait.start();
	 }
	    
	    private void resetButtons() {
	    	for (int i=0; i<16; i++) {
	    		adjacent_buttons[i] = true;
	    		unused_buttons[i] = true;
	    		valid_buttons[i] = true;
	    		
	 			ImageView iv = (ImageView) findViewById(imageViewUsed[i]);
	 			iv.setImageResource(button_imgs[(alphvals[i])-65]);
	 			System.out.println("position " + i + ": " + alphvals[i] + " -- " + alphvals[i]);
	 			System.out.println("imageViewUsed["+i+"]: "+imageViewUsed[i]);
	 		 }
	    	
	    }
	    
	    private void setPushedButton(int pos){
	    	unused_buttons[pos]=false;
	    }
	    
	    private void setAdjacentButtons(int pos){
	    	for (int i=0; i<16; i++){
	    		adjacent_buttons[i] = false;
	    	}
	    	switch (pos) {
	    	case 0: 
			    adjacent_buttons[1] = true;
			    adjacent_buttons[4] = true;
			    adjacent_buttons[5] = true;
			    break;
	    	case 1: 
	    		adjacent_buttons[0] = true;
			    adjacent_buttons[2] = true;
			    adjacent_buttons[4] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[6] = true;
			    break;
	    	case 2:
	    		adjacent_buttons[1] = true;
			    adjacent_buttons[3] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[7] = true;
			    break;
	    	case 3:
	    		adjacent_buttons[2] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[7] = true;
			    break;
	    	case 4:
	    		adjacent_buttons[0] = true;
			    adjacent_buttons[1] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[8] = true;
			    adjacent_buttons[9] = true;
			    break;
	    	case 5:
	    		adjacent_buttons[0] = true;
			    adjacent_buttons[1] = true;
			    adjacent_buttons[2] = true;
			    adjacent_buttons[4] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[8] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[10] = true;
			    break;
	    	case 6:
	    		adjacent_buttons[1] = true;
			    adjacent_buttons[2] = true;
			    adjacent_buttons[3] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[7] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[11] = true;
			    break;
	    	case 7:
	    		adjacent_buttons[2] = true;
			    adjacent_buttons[3] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[11] = true;
			    break;
	    	case 8:
	    		adjacent_buttons[4] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[12] = true;
			    adjacent_buttons[13] = true;
			    break;
	    	case 9:
	    		adjacent_buttons[4] = true;
			    adjacent_buttons[5] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[8] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[12] = true;
			    adjacent_buttons[13] = true;
			    adjacent_buttons[14] = true;
			    break;
	    	case 10:
	    		adjacent_buttons[5] = true;
			    adjacent_buttons[6] = true;
			    adjacent_buttons[7] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[11] = true;
			    adjacent_buttons[13] = true;
			    adjacent_buttons[14] = true;
			    adjacent_buttons[15] = true;
			    break;
	    	case 11:
	    		adjacent_buttons[6] = true;
			    adjacent_buttons[7] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[14] = true;
			    adjacent_buttons[15] = true;
			    break;
	    	case 12:
	    		adjacent_buttons[8] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[13] = true;
			    break;
	    	case 13:
	    		adjacent_buttons[8] = true;
			    adjacent_buttons[9] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[12] = true;
			    adjacent_buttons[14] = true;
			    break;
	    	case 14:
	    		adjacent_buttons[9] = true;
			    adjacent_buttons[10] = true;
			    adjacent_buttons[11] = true;
			    adjacent_buttons[13] = true;
			    adjacent_buttons[15] = true;
			    break;
	    	case 15:
	    		adjacent_buttons[10] = true;
			    adjacent_buttons[11] = true;
			    adjacent_buttons[14] = true;
			    break;
	    	}
	    }
	    
	    private void setValidButtons(boolean[] arr1, boolean[] arr2){
	    	for (int i=0; i<16; i++) {
	    		valid_buttons[i] = arr1[i] & arr2[i];
	    	}
	    }
	    
	    
	    private boolean isValidButtonPushed(int pos) {
	    	if (valid_buttons[pos]==true) 
	    	  	return true;
	    	else return false;
	    }
	    
	    private void calcScore(int wordLength) {
	    			
	    	if (wordLength<3)
	    		score += 0;
	    	else if (wordLength==3 || wordLength==4)
	    		score++;
	    	else if (wordLength==5)
	    		score +=2;
	    	else if (wordLength==6)
	    		score +=3;
	    	else if (wordLength==7)
	    		score +=5;
	    	else //if (wordLength>7)
	    		score +=11;
	    	
	    	score_tv.setText(score + "");
	    }
	    

	    private static void printMe(List<String> l){
	    		for(String b : l)
	    			System.out.printf("%s", b);
	    		System.out.println();
	}
}