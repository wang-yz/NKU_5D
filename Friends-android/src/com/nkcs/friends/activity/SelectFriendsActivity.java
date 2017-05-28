package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.FollowFriendsTask;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.SelectFriendsTask;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFriendsActivity extends Activity {
	
	private ListView listMaybe;
	public List<Users> dataList = new ArrayList<Users>();
	public MyAdapter adapter;
	private int friendsid;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_friends);
		
		listMaybe = (ListView) findViewById(R.id.listMaybe);
		adapter = new MyAdapter();
		listMaybe.setAdapter(adapter);
		listMaybe.setOnItemClickListener(new onItemClickListenerImpl());
		
		MyApp myApp = (MyApp) getApplication();
		Users users = myApp.getUser();
		
		// new SelectFriendsTask(this).execute("0301", "111");
		
	}
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			view = View.inflate(getApplicationContext(), R.layout.item_selectfriends, null);
			
			Users users = dataList.get(position);	
			
			ImageView imgSelectFriends = (ImageView) view.findViewById(R.id.imgSelectFriends);
			String imageURL = users.getPhoto();
			new FriendsPhotoTask(imgSelectFriends, SelectFriendsActivity.this).execute(imageURL);
			TextView txtSelectName = (TextView)view.findViewById(R.id.txtSelectName);
			txtSelectName.setText(users.getNickname());
			return view;
		}
	}

	private class onItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			System.out.println("1111111111111");
			friendsid = dataList.get(position).getUserid();
			
			MyApp myApp = (MyApp) getApplication();
//			Users users = myApp.getUser();
//			String usersid = users.getUserid() + "";
			System.out.println("friendsid =" + friendsid);
			new FollowFriendsTask(SelectFriendsActivity.this).execute("1", friendsid + "");
			Toast.makeText(getApplicationContext(), "你已成功关注该好友，可以到好友列表中查看", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_friends, menu);
		return true;
	}

}
