package com.nkcs.friends.fragment;

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
import com.nkcs.friends.xlistview.ListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LatestFragment extends Fragment implements OnTouchListener, IXListViewListener {
	
	private List<Activities> dataActivity; // 总容器
	// private List<Activities> tempActivity; // 临时容器，用于存放新的pageSize条数据

	private ActivityAdapter adapterActivity;
	private String int_name = "Latest";
	private String startNum = "0";
	private String pageSize = "10";

	// 显示所有活动列表
	private ListView lvwActivity;
	// 整个顶部搜索控件，用于隐藏和显示底部整个控件
	// private LinearLayout ll_search_latest;
	// 返回按钮
	private ImageView iv_back_latest;
	@SuppressWarnings("unused")
	// private EditText ed_search_latest;

	private AnimationSet animationSet;
	/** 第一次按下屏幕时的Y坐标 */
	float fist_down_Y = 0;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View latestLayout = inflater.inflate(R.layout.fragment_latest_layout, container, false);
		
		
		// initView
//		ll_search_latest = (LinearLayout) latestLayout.findViewById(R.id.ll_choice_latest_fragment);
//		ed_search_latest = (EditText) latestLayout.findViewById(R.id.ed_Searchware_latest_fragment);
//		iv_back_latest = (ImageView) latestLayout.findViewById(R.id.iv_back_latest_fragment);
//		iv_back_latest.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				getActivity().finish();
//			}
//		});
		lvwActivity = (ListView) latestLayout.findViewById(R.id.listView_ware_latest);
		lvwActivity.setOnTouchListener(this);
		((com.nkcs.friends.xlistview.ListView) lvwActivity).setXListViewListener(this);
		// 设置可以进行下拉加载的功能
		((com.nkcs.friends.xlistview.ListView) lvwActivity).setPullLoadEnable(true);
		((com.nkcs.friends.xlistview.ListView) lvwActivity).setPullRefreshEnable(true);

		try {
			this.dataActivity = new ArrayList<Activities>();
			this.adapterActivity = new ActivityAdapter(getActivity().getApplicationContext(),
					dataActivity);

			this.lvwActivity.setAdapter(adapterActivity);
			this.lvwActivity.setOnItemClickListener(new OnItemClickListenerImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showActivity();
		return latestLayout;
	}
	
	private void showActivity() {

		Toast.makeText(getActivity().getApplicationContext(), "显示最新活动", Toast.LENGTH_SHORT).show();

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
			MyApp myApp = (MyApp) getActivity().getApplication();
			myApp.setActivity(transActivities);

			if (transActivities == null) {
				Toast.makeText(getActivity().getApplicationContext(), "Activity数据为空", Toast.LENGTH_SHORT).show();
			}
			Intent intent = new Intent(getActivity(),ActivityInfoActivity.class);
			getActivity().startActivity(intent);
		}

	}
	

	// --------------------------------------------------------------------------

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {

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

	
}
