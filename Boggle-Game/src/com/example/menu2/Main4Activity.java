package com.example.menu2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.os.Bundle;
import android.widget.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.GridView;
import android.widget.BaseAdapter;
import java.math.*;

public class Main4Activity extends Activity {
	public String[] alphabets = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	//public String alphabets ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	Random rn = new Random();
	//int index = rn.nextInt(25);	
	public class ButtonAdapter extends BaseAdapter {  
			 private Context mContext;  
			 public ButtonAdapter(Context c) {  
				  mContext = c;  
				 }  
    

	public int getCount() {
		// TODO Auto-generated method stub
		return buttons.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Button btn;  
		
		  if (convertView == null) {  
		   // if it's not recycled, initialize some attributes  
		   btn = new Button(mContext);  
		   //btn.setLayoutParams(new GridView.LayoutParams(100, 55)); 
		   btn.setLayoutParams(new GridView.LayoutParams(100,100));
		   btn.setPadding(8, 8, 8, 8);  
		   }  
		  else {  
		   btn = (Button) convertView;  
		  }  
		  //btn.set
		  Collections.shuffle(Arrays.asList(alphabets));
		  //btn.setText((alphabets[rn.nextInt(25)]));
		  btn.setText(alphabets[rn.nextInt(26)]);
		  //btn.setText(alphabets.charAt(rn.nextInt(25)));
		  //btn.setText(rn.nextInt('Z'-'A'+1));
		  btn.setTextColor(Color.YELLOW);
		  btn.setTextSize(16);
		  btn.setTextSize(16);
		  return btn;
	}
	private Integer[] buttons = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	
		}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        GridView gridview = (GridView) findViewById(R.id.gridView1);  
        gridview.setAdapter(new ButtonAdapter(this));  
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main4, menu);
        return true;
    
}
}
