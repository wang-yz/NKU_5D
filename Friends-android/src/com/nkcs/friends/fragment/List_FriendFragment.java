package com.nkcs.friends.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.DeleteFriendsTask;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.MyFriendsTask;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class List_FriendFragment extends Fragment {

	private ListView listShowMyFriends;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	private Dialog dlgDelete;
	private int num;
	public MyAdapter adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View viewFriendList = inflater.inflate(R.layout.fragment_friend_list, null);
		
		listShowMyFriends = (ListView) viewFriendList.findViewById(R.id.lvwMyFriend);		
		
		System.out.println("Can run into List_FriendFragment");
		
		MyApp myApp = (MyApp) getActivity().getApplication();
		Users user = myApp.getUser();
		
		System.out.println("Can run into List_FriendFragment and current user is: " + user.getUsername());
		
		adapter = new MyAdapter();
		listShowMyFriends.setAdapter(adapter);
		
		listShowMyFriends.setOnItemLongClickListener(new OnItemLongClickListenerImpl());
		
		new MyFriendsTask(this, dataList).execute(user.getUsername(), user.getPassword());
		
		Builder builder = new Builder(getActivity());
		builder.setTitle("删除");
		builder.setMessage("确认删除该关注的好友吗？");
		builder.setIcon(R.drawable.question);
		builder.setPositiveButton("确认", new DeleteOnClickListenerImpl());
		builder.setNegativeButton("取消", null);
		this.dlgDelete = builder.create();
		
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
		public View getView(int position, View view, ViewGroup parent) {
			view = View.inflate(getActivity().getApplicationContext(), R.layout.item_myfriends, null);
			
			Map<String, Object> map = dataList.get(position);
			
			ImageView imgMyFriends = (ImageView)view.findViewById(R.id.imgMyFriends);
			String imageURL = map.get("photo").toString();
			//imageURL += "2.gif";
			System.out.println("imageURL="+imageURL);
			new FriendsPhotoTask(imgMyFriends, getActivity()).execute(imageURL);	
			TextView txtFriendsName = (TextView)view.findViewById(R.id.txtFriendName);
			TextView txtFriendsNumber = (TextView)view.findViewById(R.id.txtFriendNumber);
			txtFriendsName.setText(map.get("nickname").toString());			
			txtFriendsNumber.setText(map.get("phonenumber").toString());	
			
			Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/FontNormal.ttf");
			
			txtFriendsName.setTypeface(typeface);
			txtFriendsNumber.setTypeface(typeface);
			
			return view;
		}
	}

	private class OnItemLongClickListenerImpl implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			dlgDelete.show();
			num = arg2;
			
			return false;
		}
		
	}
	
	private class DeleteOnClickListenerImpl implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int id) {

			if(id==Dialog.BUTTON_POSITIVE){
				Map<String, Object> map = new HashMap<String, Object>();
				dataList.remove(num);
				map = dataList.get(num);
				String nickname = map.get("nickname").toString();
				String phonenumber = map.get("phonenumber").toString();
				
				MyApp myApp = (MyApp) getActivity().getApplication();
				Users user = myApp.getUser();
				new DeleteFriendsTask(getActivity()).execute(nickname, phonenumber, user.getUserid()+"");
				
				adapter.notifyDataSetChanged();
			}
		}		
	}

}
