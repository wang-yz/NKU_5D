package com.nkcs.friends.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.activity.ActivityActivity;
import com.nkcs.friends.activity.SecondInterestActivity;
import com.nkcs.friends.adapter.InterestAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.NotificationTask;
import com.nkcs.friends.task.ShowInterestTask;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class InterestFragment extends Fragment {
	
	private ListView lvwShow;
	private int status = 1; // 用于表明当前页面显示状态: 1.一级标签 2.二级标签 3.活动列表
	public List<Interests> dataInterest;
	public InterestAdapter adapterInterest;
	private String username, password;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View interestLayout = inflater.inflate(R.layout.fragment_interest_layout, container, false);
		
		this.lvwShow = (ListView) interestLayout.findViewById(R.id.lvwShowInterest);

		this.dataInterest = new ArrayList<Interests>();
		this.adapterInterest = new InterestAdapter(interestLayout.getContext(), this.dataInterest);
		this.lvwShow.setAdapter(adapterInterest);
		this.lvwShow.setOnItemClickListener(new OnItemClickListenerImpl());
		
		showInterestFirst();
		
		MyApp myApp = (MyApp)getActivity().getApplication();
		Users users = myApp.getUser();
		username = users.getUsername();
		password = users.getPassword();
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true){
//					try {
//						Thread.sleep(600000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					new NotificationTask(getActivity()).execute(username, password);
//				}
//			}
//		}).start();
		
		return interestLayout;
	}


	public void showInterestFirst() {
		ShowInterestTask showInterestFirst = new ShowInterestTask(this);
		showInterestFirst.execute("first");
		status = 2;
	}
	
	private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String firstInterest = dataInterest.get(position).getInt_name();

			// 跳转到兴趣活动界面
			Intent intent = new Intent(getActivity(), SecondInterestActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("firstInterest", firstInterest);
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
		
	}
	
}
