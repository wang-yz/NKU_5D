package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.adapter.InterestAdapter;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.task.ShowSecondInterestTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SecondInterestActivity extends Activity {

	private String firstInterest;
	private ListView lvwShow;
	public List<Interests> dataInterest;
	public InterestAdapter adapterInterest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_interest);

		this.lvwShow = (ListView) findViewById(R.id.lvwShowInterest_second);
		this.dataInterest = new ArrayList<Interests>();
		this.adapterInterest = new InterestAdapter(this, this.dataInterest);
		this.lvwShow.setAdapter(adapterInterest);
		this.lvwShow.setOnItemClickListener(new OnItemClickListenerImpl());

		// Toast.makeText(getApplicationContext(), "跳转到了SecondInterestActivity", Toast.LENGTH_SHORT).show();
		
		showSecondInterest();
	}

	public void showSecondInterest() {

//		// 获取意图对象
//		Intent intent = this.getIntent();
//		// 接收参数
//		Bundle bundle = intent.getExtras();
		firstInterest = "home";

//		try {
//			firstInterest = bundle.getString("firstInterest");
//			if (firstInterest == null) {
//				firstInterest = "none";
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// Toast.makeText(getApplicationContext(), "firstInterest是" + firstInterest, Toast.LENGTH_SHORT).show();

		// dataInterest = new ArrayList<Interests>();
		adapterInterest.notifyDataSetInvalidated();
		ShowSecondInterestTask showSecondInterestSecond = new ShowSecondInterestTask(this);
		showSecondInterestSecond.execute(firstInterest);
	}

	private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String parentName = dataInterest.get(position).getInt_name();

			// 跳转到兴趣活动界面
			Intent intent = new Intent(SecondInterestActivity.this, ActivityActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("activityShowModel", "interest");
			// bundle.putString("int_name", parentName);
			bundle.putString("int_name", "food");
			
			intent.putExtras(bundle);

			startActivity(intent);

			// 关闭当前页面
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second_interest, menu);
		return true;
	}

}
