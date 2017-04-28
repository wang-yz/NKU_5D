package com.nkcs.friends.task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.nkcs.friends.activity.ChatActivity;
import com.nkcs.friends.app.MyApp;

import android.os.AsyncTask;

public class ChatTask extends AsyncTask<String, Void, String> {
    ChatActivity chatActivity;
    public ChatTask(ChatActivity chatActivity) {
		super();
		this.chatActivity = chatActivity;
	}

	@Override
	protected String doInBackground(String... arg0) {
		
		String ip = arg0[0];
		String port = arg0[1];
		
		chatActivity.s = new Socket();
		chatActivity.isa = new InetSocketAddress(ip,Integer.parseInt(port)); 
        try {
        	chatActivity.s.connect(chatActivity.isa,1000);
			if(chatActivity.s.isConnected()){
				chatActivity.isContect = true;
				return "a";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		return null;
	}
}
