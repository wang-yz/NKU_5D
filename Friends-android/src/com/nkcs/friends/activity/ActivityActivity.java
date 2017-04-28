package com.nkcs.friends.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.task.ShowActivityTask;
import com.nkcs.friends.xlistview.ListView;
import com.nkcs.friends.xlistview.ListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityActivity extends Activity implements OnTouchListener, IXListViewListener{

	// 活动部分
	private List<Activities> dataActivity; // 总容器
	private List<Activities> tempActivity; // 临时容器，用于存放新的pageSize条数据
	
	private ActivityAdapter adapterActivity;
	private String activityShowModel;
	private String int_name;
	private String startNum = "0";
	private String pageSize = "10";
	
	// 显示所有活动列表
	private ListView lvwActivity;
	// 整个顶部搜索控件，用于隐藏和显示底部整个控件
	private LinearLayout ll_search;
	// 返回按钮
	private ImageView iv_back;
	@SuppressWarnings("unused")
	private EditText ed_search;
	
	private AnimationSet animationSet;
	/**第一次按下屏幕时的Y坐标*/
	float fist_down_Y = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		
		initView();

		try {
			// 活动部分
			this.dataActivity = new ArrayList<Activities>();
			this.adapterActivity = new ActivityAdapter(getApplicationContext(), dataActivity);

			this.lvwActivity.setAdapter(adapterActivity);
			this.lvwActivity .setOnItemClickListener(new OnItemClickListenerImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showActivity();
	}
	
	private void initView() {
		ll_search = (LinearLayout) findViewById(R.id.ll_choice);
//		ed_search = (EditText) findViewById(R.id.ed_Searchware);
//		iv_back = (ImageView) findViewById(R.id.iv_back);
//		iv_back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});

		lvwActivity = (ListView) findViewById(R.id.listView_ware);
		lvwActivity.setOnTouchListener(this);
		lvwActivity.setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		lvwActivity.setPullLoadEnable(true);
		lvwActivity.setPullRefreshEnable(true);
	}

	private void showActivity() {
		// 获取意图对象
		Intent intentActivity = this.getIntent();
		// 接收参数
		Bundle bundle = intentActivity.getExtras();
		
		try {
			activityShowModel = bundle.getString("activityShowModel");
			if (activityShowModel == null) {
				activityShowModel = "empty_activityShowModel";
			}
			
			int_name = bundle.getString("int_name");
			if (int_name == null) {
				int_name = "empty_int_name";
			}
			
			//Toast.makeText(getApplicationContext(), activityShowModel + int_name, Toast.LENGTH_SHORT).show();

			if (activityShowModel.equals("interest")) {
				ShowActivityTask showActivity = new ShowActivityTask(dataActivity, lvwActivity, adapterActivity);
				showActivity.execute(int_name, startNum, pageSize);
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "接收异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	private class OnItemClickListenerImpl implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			System.out.println(position + "");
			Activities transActivities =  dataActivity.get(position-1);
			// 将activity对象保存到全局变量中
			MyApp myApp = (MyApp) getApplication();
			myApp.setActivity(transActivities);
			// System.out.println(myApp.getActivity() + "==============================");
			// System.out.println("liu transActivities.getAct_id()="+transActivities.getAct_id());
			if (transActivities==null){
				Toast.makeText(getApplicationContext(), "传递活动对象错误", Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent(ActivityActivity.this, ActivityInfoActivity.class);
			startActivity(intent);
		}

	}

	// --------------------------------------------------------------------------

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//第一次按下时的Y坐标
			fist_down_Y = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// 向上滑动，隐藏搜索栏
			if (fist_down_Y - y > 250 && ll_search.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
				ll_search.startAnimation(animationSet);
				ll_search.setY(-100);
				ll_search.setVisibility(View.GONE);
			}
			// 向下滑动，显示搜索栏
			if (y - fist_down_Y > 250 && !ll_search.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
				ll_search.startAnimation(animationSet);
				ll_search.setY(0);
				ll_search.setVisibility(View.VISIBLE);
			}
			break;

		}
		return false;
	}

	@Override
	public void onRefresh() {
		dataActivity.clear();
		startNum = "0";
		showActivity();

		// 停止刷新和加载
		onLoad();		
	}

	@Override
	public void onLoadMore() {
		int intStartNum = Integer.parseInt(startNum);
		intStartNum += Integer.parseInt(pageSize);
		startNum = intStartNum + "";
		showActivity();
	
	}
	
	/**简单的时间格式*/
	private void onLoad() {
		((com.nkcs.friends.xlistview.ListView) lvwActivity).stopRefresh();
		// 停止加载更多
		((com.nkcs.friends.xlistview.ListView) lvwActivity).stopLoadMore();

		// 设置最后一次刷新的时间
		((com.nkcs.friends.xlistview.ListView) lvwActivity).setRefreshTime(getCurrentTime(System.currentTimeMillis()));
	}

	/**简单的时间格式*/
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	public static String getCurrentTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));

	}

	// --------------------------------------------------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu_addactivity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ab_AddActivity:
			Intent intent = new Intent(ActivityActivity.this, PostActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
		
		return true;
		
	}

}
