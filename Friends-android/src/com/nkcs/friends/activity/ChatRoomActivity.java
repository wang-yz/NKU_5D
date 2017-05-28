package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.ChatRoomTask;
import com.nkcs.friends.task.FriendsPhotoTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatRoomActivity extends Activity {

	private ListView listViewChat;
	
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	
	private MyAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_room);
		
		listViewChat=(ListView) findViewById(R.id.listViewChat);
		
		adapter = new MyAdapter();
		listViewChat.setAdapter(adapter);
		listViewChat.setOnItemClickListener(new OnItemClickListenerImpl());
		
//		MyApp myApp = (MyApp) getApplication();
//		
//		Users user = myApp.getUser();
//		
//		String userid = user.getUserid() + "";
		
		String userid = 1 + "";
		
		// new ChatRoomTask(this).execute(userid);
	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {

			view = View.inflate(getApplicationContext(), R.layout.item_chat_room,null);

			if (position % 2 == 0) {
				view.setBackgroundColor(Color.parseColor("#b3F5F7F8"));
			} else {
				view.setBackgroundColor(Color.parseColor("#e2e6e9"));
			}

			Map<String, Object> map = dataList.get(position);

			ImageView imgActivity = (ImageView)view.findViewById(R.id.imageChatPhoto);
			String imageURL = map.get("photo").toString();
			System.out.println("imageURL = " + imageURL);
			//先试了再改
			new FriendsPhotoTask(imgActivity, ChatRoomActivity.this).execute(imageURL);	
			
			TextView txtChatName = (TextView)view.findViewById(R.id.txtChatName);
			TextView txtChatCurNum = (TextView)view.findViewById(R.id.txtChatCurNum);
			
			txtChatName.setText( "聊天室名称 ：" + map.get("chatname").toString());
			txtChatCurNum.setText( "当前人数 ：" + map.get("curnum").toString());
			
			return view;
		}

	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String act_id = dataList.get(position).get("act_id").toString();
			
			System.out.println("act_id = " + act_id);
			
			Intent intent = new Intent(ChatRoomActivity.this,ChatActivity.class);
			intent.putExtra("act_id", act_id);
			startActivity(intent);
			
		}
		
	}
	
	public List<Map<String, Object>> getDatalist(){
		return dataList;
	}
	
	public MyAdapter getAdapter(){
		return adapter;
	}

}
