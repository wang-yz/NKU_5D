package com.nkcs.friends.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.ChatTask;
import com.nkcs.friends.task.GetChatRecordTask;
import com.nkcs.friends.task.SaveMessageTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends Activity {

	public EditText chatbox, chattxt;
	private Button chatok;
	
	private Thread thread = null;
	public Socket s = null;
	public InetSocketAddress isa = null; 
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String reMsg = null;
    public boolean isContect = false;//联系
    private String username, password, activityId;
	
    private String chatKey="SLEEKNETGEOCK4stsjeS";//sleeknetgeock4stsjes,这个是干嘛的不知道
    private String nickname = "小小", ip="192.168.40.32", port="8088";	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		chatbox = (EditText) findViewById(R.id.chatbox);
		chattxt = (EditText) findViewById(R.id.chattxt);
		chatok = (Button) findViewById(R.id.chatok);
        chatbox.setCursorVisible(false);
        chatbox.setFocusable(false);
        chatbox.setFocusableInTouchMode(false);
        chatbox.setGravity(2);
        
        Intent intent1 = this.getIntent();
        activityId = intent1.getStringExtra("act_id");
        new GetChatRecordTask(this).execute(activityId);        
        
        MyApp myApp = (MyApp) getApplication();
        Users users = myApp.getUser();
        username = users.getUsername();
        password = users.getPassword();
        
//        username = "0301";
//        password = "111";
        
		nickname = users.getNickname();
		//ip = users.getIp();
		ip = "60.205.30.58";
		port = "8080";
		
        //设置连接
        if(ip==null || port==null){
        	Toast.makeText(getApplicationContext(), "您的网络地址有误，请重新登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
            startActivity(intent);
            ChatActivity.this.finish();
        }
        else if(nickname==null){
        	Toast.makeText(getApplicationContext(), "您未设置昵称，以下将用您的用户名！", Toast.LENGTH_SHORT).show();
        	nickname = users.getUsername();
        }
        else{
            connect();
            new Thread(new getMessage()).start();
            chatok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = chattxt.getText().toString().trim();
                    System.out.println(s);
                    try {
                        dos.writeUTF(chatKey+"name:"+nickname+"end;"+str);
                        new SaveMessageTask(ChatActivity.this).execute(username, password, str, activityId);
                        chattxt.setText("");
                    }catch (SocketTimeoutException  e) {
                          System.out.println("连接超时，服务器未开启或IP错误");
                          Toast.makeText(ChatActivity.this, "连接超时，服务器未开启或IP错误", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
                          startActivity(intent);
                          ChatActivity.this.finish();
                          e.printStackTrace();
                      } catch (IOException e) {
                        // TODO Auto-generated catch block
                          System.out.println("连接超时，服务器未开启或IP错误");
                          Toast.makeText(ChatActivity.this, "连接超时，服务器未开启或IP错误", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(ChatActivity.this,LoginActivity.class);
                          startActivity(intent);
                          ChatActivity.this.finish();
                          e.printStackTrace();
                    }
                }
            });
        }
		
	}
	
    public void connect() {
    	new ChatTask(this).execute(ip, port);
        try {
        	
        	while(!isContect){
        		
        	}
        	if(isContect){
			    dos = new DataOutputStream (s.getOutputStream());
			    dis = new DataInputStream (s.getInputStream());
			    dos.writeUTF(chatKey+"online:"+nickname);
			    System.out.println(0);
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public class getMessage implements Runnable{
		@Override
		public void run() {
	        if (isContect) {
	            try {
	                while ((reMsg = dis.readUTF()) != null) {
	                    System.out.println(reMsg);
	                    if (reMsg != null) {

	                        try {
	                            Message msgMessage = new Message();
	                            msgMessage.what = 0x1981;
	                            handler.sendMessage(msgMessage);
	                            Thread.sleep(100);
	                        } catch (InterruptedException e) {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
	                        }

	                    }
	                }
	            } catch (SocketException e) {
	                // TODO: handle exception
	                System.out.println("exit!");
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
		}
    	
    }
    
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 0x1981:
                chatbox.setText(chatbox.getText() + reMsg + '\n');
                chatbox.setSelection(chatbox.length());
                break;
            }
        }
    };
    
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        disConnect();
//        //System.exit(0);
//    }
//    
//    
//    public void disConnect() {
//        if(dos!=null){
//        try {
//            
//                dos.writeUTF(chatKey+"offline:"+nickname);
//            
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        try {
//            s.close();
//        } catch (IOException e) {
//              e.printStackTrace();
//        }
//        }
//    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

}
