package com.example.menu2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        Button next = (Button) findViewById(R.id.bbasic);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                
               
                Intent myIntent2 = new Intent(view.getContext(), Main5Activity.class);
                startActivity(myIntent2);
            }

        });
    }
    public void onCreate1(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.main2);

        Button next1 = (Button) findViewById(R.id.b2basic);
        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent();
            	setResult(RESULT_OK, intent);
                finish();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
}
