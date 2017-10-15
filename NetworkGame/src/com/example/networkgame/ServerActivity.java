package com.example.networkgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.net.*;
import java.io.*;
import java.util.Enumeration;

public class ServerActivity extends Activity {
	private TextView serverStatus;
	 public static String SERVERIP ;
	 public static final int SERVERPORT = 6000;
	 private Handler handler = new Handler();
	 private ServerSocket serverSocket;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        serverStatus = (TextView) findViewById(R.id.server_status);
        SERVERIP = getLocalIpAddress();
        System.out.println("The serverIp is :"+ SERVERIP);
        Thread fst = new Thread(new ServerThread());
        fst.start();
        
        
    }
    public class ServerThread implements Runnable {
    	 public void run() {
    		 try {
    			 if (SERVERIP != null) {
    				 handler.post(new Runnable() {
    					 public void run() {
    						 serverStatus.setText("Listening on IP: " + SERVERIP);
    						 
    					 }
    				 });
    				 serverSocket = new ServerSocket(SERVERPORT);
    				 while (true) {
    					 Socket client = serverSocket.accept();
    					 handler.post(new Runnable() {
    						 public void run() {
    							 serverStatus.setText("Connected.");
    						 }
    					 });
    					 try {
    						 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    						 String line = null;
    						 while ((line = in.readLine()) != null) {
    							 Log.d("ServerActivity", line);
    							 handler.post(new Runnable() {
    								 public void run() {
    							 }
    						 });
    							 
    						 
    					 }
    						 break;
    						 
    				 }catch (Exception e) {
    					 handler.post(new Runnable() {
    						 public void run() {
    							 serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");
    						 }
    					 });
    					 e.printStackTrace();
    				 }
    			 }
    		 }else {
    			 handler.post(new Runnable() {
    				 public void run() {
    					 serverStatus.setText("Couldn't detect internet connection.");
    					 
    				 }
    			 });
    		 }
    	 }catch (Exception e) {
    		 handler.post(new Runnable() {
    			 public void run() {
    				 serverStatus.setText("Error");
    			 }
    		 });
    		 e.printStackTrace();
    	 }
    }
    }
    private String getLocalIpAddress() {
    	 try {
    		 for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
    			 NetworkInterface intf = en.nextElement();
    			 for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
    				 InetAddress inetAddress = enumIpAddr.nextElement();
    				 if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
    				 
    			 }
    		 }
    		 
    	 } catch (SocketException ex) {
    		 Log.e("ServerActivity", ex.toString());
    	 }
    	 return null;
    }
    protected void onStop() {
    	super.onStop();
    	try{
    		 serverSocket.close();
    	 } catch (IOException e) {
    		 e.printStackTrace();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_server, menu);
        return true;
    }
}
