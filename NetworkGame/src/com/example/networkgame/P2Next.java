package com.example.networkgame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class P2Next extends Activity {
	Button host,client;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_next);
        host = (Button) findViewById(R.id.host);
        host.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent hostGame = new Intent(v.getContext(),ServerActivity.class);
				startActivity(hostGame);
				
			}
		});
    
    
    client = (Button) findViewById(R.id.client);
    client.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent clientGame = new Intent(v.getContext(),ClientActivity.class);
			startActivity(clientGame);
			
		}
	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_p2_next, menu);
        return true;
    }
}
