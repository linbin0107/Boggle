package com.example.networkgame;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class P2Activity extends Activity {
	private boolean server;
	
	Button connect,host;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2);
       
        connect = (Button) findViewById(R.id.button1);
       // connect.setClickable(true);
        connect.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//server = false;
				
				Intent myIntent = new Intent(v.getContext(),
						P2Next.class);
				startActivity(myIntent);
				}
				

			});
		}
       
			
    	
			

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_p2, menu);
        return true;
    }
}
