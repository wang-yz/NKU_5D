package com.nkcs.friends.ai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.activity.UserCalendarActivity;
import com.nkcs.friends.activity.UserUpdateActivity;
import com.nkcs.friends.ai.ChatMessage.Type;
import com.nkcs.friends.fragment.FragmentManagerActivity;
import com.nkcs.friends.fragment.FriendActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AIActivity extends Activity {

	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;

	private EditText mInputMsg;
	private Button mSendMsg;

	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			ChatMessage fromMessge = (ChatMessage) msg.obj;
			mDatas.add(fromMessge);
			mAdapter.notifyDataSetChanged();
			mMsgs.setSelection(mDatas.size()-1);
		};

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ai);
		
		initView();
		initDatas();
		initListener();
	}

	private void initListener()
	{
		mSendMsg.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String toMsg = mInputMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg))
				{
					Toast.makeText(AIActivity.this, "要问小菲什么呢？", Toast.LENGTH_SHORT).show();
					return;
				}
				
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size()-1);
				
				mInputMsg.setText("");
				
				new Thread()
				{
					public void run()
					{
						ChatMessage fromMessage = Interaction.sendMessage(toMsg);
						String response = fromMessage.getMsg();
						if (response.equals("修改个人信息")) {
							Intent intent = new Intent(AIActivity.this, UserUpdateActivity.class);
							startActivity(intent);
						} else if (response.equals("查看日历")) {
							Intent intent = new Intent(AIActivity.this, UserCalendarActivity.class);
							startActivity(intent);
						} else if (response.equals("选择兴趣")) {
							Intent intent = new Intent(AIActivity.this, FragmentManagerActivity.class);
							startActivity(intent);
						} else if (response.equals("查看好友")) {
							Intent intent = new Intent(AIActivity.this, FriendActivity.class);
							startActivity(intent);
						} else {
							Message m = Message.obtain();
							m.obj = fromMessage;
							mHandler.sendMessage(m);
						}
					};
				}.start();
			}
		});
	}

	private void initDatas()
	{
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("嗨，我是小菲，可以帮到你什么？", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mMsgs.setAdapter(mAdapter);
	}

	private void initView()
	{
		mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.id_input_msg);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
	}

}
