package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.DeleteFriendsTask;
import com.nkcs.friends.task.ImageLoadTask;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.MyFriendsTask;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class MyFriendsActivity extends Activity {

	private ListView listShowMyFriends;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
//	private ImageView imgMyFriends;
//	private TextView txtFriendsName;
//	private TextView txtFriendsNumber;
	public MyAdapter adapter;
	private Dialog dlgDelete;
	private int num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_friends);
		
		//listShowMyFriends = (ListView) findViewById(R.id.listShowMyFriendsFragment);
		
//		txtFriendsName = (TextView)findViewById(R.id.txtFriendsName);
//		txtFriendsNumber = (TextView)findViewById(R.id.txtFriendsNumber);
		
//		
//		MyApp myApp = (MyApp) getApplication();
//		Users user = myApp.getUser();
//		
//		
//		adapter = new MyAdapter();
//		listShowMyFriends.setAdapter(adapter);
//		listShowMyFriends.setOnItemLongClickListener(new OnItemLongClickListenerImpl());
//		
//		//new MyFriendsTask(this, dataList).execute(user.getUsername(), user.getPassword());
//		
//		//new MyFriendsTask(this, dataList).execute("user1", "1");
//		
//		Builder builder = new Builder(this);		
//		builder.setTitle("删除");
//		builder.setMessage("确认删除该关注的好友吗？");
//		builder.setIcon(R.drawable.question);
//		builder.setPositiveButton("确认", new DeleteOnClickListenerImpl());
//		builder.setNegativeButton("取消", null);
//		this.dlgDelete = builder.create();

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
			view = View.inflate(getApplicationContext(), R.layout.item_myfriends, null);
			
			Map<String, Object> map = dataList.get(position);
			
			ImageView imgMyFriends = (ImageView)view.findViewById(R.id.imgMyFriends);
			String imageURL = map.get("photo").toString();
			//imageURL += "2.gif";
			System.out.println("imageURL="+imageURL);
			new FriendsPhotoTask(imgMyFriends, MyFriendsActivity.this).execute(imageURL);	
			TextView txtFriendsName = (TextView)view.findViewById(R.id.txtFriendName);
			TextView txtFriendsNumber = (TextView)view.findViewById(R.id.txtFriendNumber);
			txtFriendsName.setText(map.get("nickname").toString());			
			txtFriendsNumber.setText(map.get("phonenumber").toString());	
			return view;
		}
	}
	
	private class OnItemLongClickListenerImpl implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
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
				
				MyApp myApp = (MyApp) getApplication();
				Users user = myApp.getUser();
				new DeleteFriendsTask(MyFriendsActivity.this).execute(nickname, phonenumber, user.getUserid()+"");
				
				adapter.notifyDataSetChanged();
			}
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_friends, menu);
		return true;
	}

}
