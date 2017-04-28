package com.nkcs.friends.fragment;


import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import com.nkcs.friends.R;
import com.nkcs.friends.activity.SelectFriendsActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.FollowFriendsTask;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.SelectFriendsTask;

public class Recommend_FriendFragment extends Fragment {

	private ListView listMaybe;
	public List<Users> dataList = new ArrayList<Users>();
	public MyAdapter adapter;
	private int friendsid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View viewFriendList = inflater.inflate(R.layout.fragment_friend_recommend, null);
		
		listMaybe = (ListView) viewFriendList.findViewById(R.id.listMaybe_fragment);
		adapter = new MyAdapter();
		listMaybe.setAdapter(adapter);
		listMaybe.setOnItemClickListener(new onItemClickListenerImpl());
		
		MyApp myApp = (MyApp) getActivity().getApplication();
		Users users = myApp.getUser();
		
		new SelectFriendsTask(this, dataList).execute(users.getUsername(), users.getPassword());
		
		return viewFriendList;
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
			view = View.inflate(getActivity().getApplicationContext(), R.layout.item_selectfriends, null);
			
			Users users = dataList.get(position);	
			
			ImageView imgSelectFriends = (ImageView) view.findViewById(R.id.imgSelectFriends);
			String imageURL = users.getPhoto();
			new FriendsPhotoTask(imgSelectFriends, getActivity()).execute(imageURL);
			TextView txtSelectName = (TextView)view.findViewById(R.id.txtSelectName);
			txtSelectName.setText(users.getNickname());
			
			Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/FontNormal.ttf");
			
			txtSelectName.setTypeface(typeface);
			
			return view;
		}
	}

	private class onItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			System.out.println("1111111111111");
			friendsid = dataList.get(position).getUserid();
			
			MyApp myApp = (MyApp) getActivity().getApplication();
//			Users users = myApp.getUser();
//			String usersid = users.getUserid() + "";
			System.out.println("friendsid =" + friendsid);
			new FollowFriendsTask(getActivity()).execute("1", friendsid + "");
			Toast.makeText(getActivity().getApplicationContext(), "你已成功关注该好友，可以到好友列表中查看", Toast.LENGTH_SHORT).show();
		}
		
	}

}
