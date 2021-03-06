package com.example.networkgame;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ClientActivity extends Activity {
	 
	 //private EditText serverIp;
	 
	    private Button connectPhones;
	 
	    private String serverIpAddress = "10.0.2.2";
	    Socket socket;
	    private static final int PORT = 5000;
	    private boolean connected = false;
	 
	    private Handler handler = new Handler();
	 
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_client);
	 
	       // serverIp = (EditText) findViewById(R.id.server_ip);
	        connectPhones = (Button) findViewById(R.id.connect_phones);
	        connectPhones.setOnClickListener( new View.OnClickListener() {
	 
	        @Override
	        public void onClick(View v) {
	            if (!connected) {
	                //serverIpAddress = serverIp.getText().toString();
	                System.out.println("serverIpAdress is :"+ serverIpAddress);
	               // if (!serverIpAddress.equals("")) {
	                    Thread cThread = new Thread(new ClientThread());
	                    cThread.start();
	               // }
	            }
	        }
	        });
	    
	    }
	    public class ClientThread implements Runnable {
	 
	        public void run() {
	            try {
	                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
	                SocketAddress sockaddr = new InetSocketAddress(serverAddr, PORT);
	                Log.d("ClientActivity", "C: Connecting...");
	                //Socket socket = new Socket(serverAddr, ServerActivity.SERVERPORT);
	                socket = new Socket();
	               socket.connect(sockaddr,5000);
	                //socket = new Socket(serverAddr,PORT);
	                connected = true;
	                while (connected) {
	                    try {
	                        Log.d("ClientActivity", "C: Sending command.");
	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
	                                    .getOutputStream())), true);
	                            // where you issue the commands
	                            out.println("Hey Server!");
	                            Log.d("ClientActivity", "C: Sent.");
	                    } catch (Exception e) {
	                        Log.e("ClientActivity", "S: Error", e);
	                    }
	                }
	                socket.close();
	                Log.d("ClientActivity", "C: Closed.");
	            } catch (Exception e) {
	                Log.e("ClientActivity", "C: Error", e);
	                connected = false;
	            }
	        }
	    }
	    }
