package com.nkcs.friends.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.activity.ActivityInfoActivity;
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

public class LatestActivity extends Activity implements OnTouchListener, IXListViewListener {

	// 活动部分
	private List<Activities> dataActivity; // 总容器
	private List<Activities> tempActivity; // 临时容器，用于存放新的pageSize条数据

	private ActivityAdapter adapterActivity;
	// private String activityShowModel;
	private String int_name = "Latest";
	private String startNum = "0";
	private String pageSize = "10";

	
	// 显示所有活动列表
	private ListView lvwActivity;
	// 整个顶部搜索控件，用于隐藏和显示底部整个控件
	private LinearLayout ll_search_latest;
	// 返回按钮
	private ImageView iv_back_latest;
	@SuppressWarnings("unused")
	private EditText ed_search_latest;

	private AnimationSet animationSet;
	/** 第一次按下屏幕时的Y坐标 */
	float fist_down_Y = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latest);

		initView();

		try {
			// 活动部分
			this.dataActivity = new ArrayList<Activities>();
			this.adapterActivity = new ActivityAdapter(getApplicationContext(), dataActivity);
			this.lvwActivity.setAdapter(adapterActivity);
			this.lvwActivity.setOnItemClickListener(new OnItemClickListenerImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showActivity();
	}

	private void initView() {
		ll_search_latest = (LinearLayout) findViewById(R.id.ll_choice_latest);
		ed_search_latest = (EditText) findViewById(R.id.ed_Searchware_latest);
		iv_back_latest = (ImageView) findViewById(R.id.iv_back_latest);
		iv_back_latest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		lvwActivity = (ListView) findViewById(R.id.listView_ware_latest);
		lvwActivity.setOnTouchListener(this);
		lvwActivity.setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		lvwActivity.setPullLoadEnable(true);
		lvwActivity.setPullRefreshEnable(true);
	}

	private void showActivity() {

		// Toast.makeText(getApplicationContext(), "显示最新活动", Toast.LENGTH_SHORT).show();

		ShowActivityTask showActivity = new ShowActivityTask(dataActivity, lvwActivity, adapterActivity);
		showActivity.execute(int_name, startNum, pageSize);
	}

	private class OnItemClickListenerImpl implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			System.out.println(position + "");
			Activities transActivities = dataActivity.get(position);
			// 将activity对象保存到全局变量中
			MyApp myApp = (MyApp) getApplication();
			myApp.setActivity(transActivities);

			if (transActivities == null) {
				Toast.makeText(getApplicationContext(), "Activity数据为空", Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent(LatestActivity.this,
					ActivityInfoActivity.class);
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
			// 第一次按下时的Y坐标
			fist_down_Y = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// 向上滑动，隐藏搜索栏
			if (fist_down_Y - y > 250 && ll_search_latest.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(
						this, R.anim.up_out);
				ll_search_latest.startAnimation(animationSet);
				ll_search_latest.setY(-100);
				ll_search_latest.setVisibility(View.GONE);
			}
			// 向下滑动，显示搜索栏
			if (y - fist_down_Y > 250 && !ll_search_latest.isShown()) {
				if (animationSet != null) {
					animationSet = null;
				}
				animationSet = (AnimationSet) AnimationUtils.loadAnimation(
						this, R.anim.down_in);
				ll_search_latest.startAnimation(animationSet);
				ll_search_latest.setY(0);
				ll_search_latest.setVisibility(View.VISIBLE);
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

	private void onLoad() {
		((com.nkcs.friends.xlistview.ListView) lvwActivity).stopRefresh();
		// 停止加载更多
		((com.nkcs.friends.xlistview.ListView) lvwActivity).stopLoadMore();

		// 设置最后一次刷新的时间
		((com.nkcs.friends.xlistview.ListView) lvwActivity)
				.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
	}

	/** 简单的时间格式 */
	public static SimpleDateFormat mDateFormat = new SimpleDateFormat(
			"MM-dd HH:mm");

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
		getMenuInflater().inflate(R.menu.latest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
