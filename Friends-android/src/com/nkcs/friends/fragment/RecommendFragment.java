package com.nkcs.friends.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.nkcs.friends.R;
import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.task.GuessActivityTask;
import com.nkcs.friends.task.GuessLikeTask;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RecommendFragment extends Fragment {
	
	private  EditText edtActName;
	private Button btnSelect;
	private ListView listView;
	public List<Activities> data;
	public ActivityAdapter adapter;
	private String txtString;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View recommendLayout = inflater.inflate(R.layout.fragment_recommend_layout, container, false);
		
		//获取组件
		edtActName=(EditText) recommendLayout.findViewById(R.id.edtActName);
		btnSelect=(Button) recommendLayout.findViewById(R.id.btnSelect);
		listView=(ListView) recommendLayout.findViewById(R.id.listViewGuess);
		
		try {
			// 活动部分
			this.data = new ArrayList<Activities>();
			this.adapter = new ActivityAdapter(getActivity().getApplicationContext(), data);

			listView.setAdapter(adapter);
			System.out.println("添加Adapter");
			this.listView .setOnItemClickListener(new OnItemClickListenerImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MyApp myApp=(MyApp) getActivity().getApplication();

		int userid=1;
		System.out.println("开始 =" + data);
		new GuessLikeTask(this).execute(userid+"");
		
		return recommendLayout;
	}
//	
//	public void getfirst(){
//		edtActName=(EditText) getActivity().findViewById(R.id.edtActName);
//		btnSelect=(Button) getActivity().findViewById(R.id.btnSelect);
//		listView=(ListView) getActivity().findViewById(R.id.listViewGuess);
//	}
	
	private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			System.out.println(position + "");
			
		}
	}
	
	public void ActNameSelect(View v){
		//txtString=edtActName.getText().toString();
		//System.out.println(txtString);
		data.clear();
		new GuessActivityTask(getActivity(), data, edtActName, adapter).execute();
	}


}
