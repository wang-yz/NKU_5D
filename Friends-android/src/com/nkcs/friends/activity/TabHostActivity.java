package com.nkcs.friends.activity;

import com.nkcs.friends.activity.LatestActivity;
import com.nkcs.friends.R;
import com.nkcs.friends.activity.ActivityInfoActivity;
import com.nkcs.friends.activity.MainActivity;
import com.nkcs.friends.activity.UserHomeActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabHostActivity extends TabActivity {

	private TabHost tabhost;
	private Intent activityInfoIntent, userHomeIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_host);

		tabhost = getTabHost();

		addTab("act1", "兴趣", MainActivity.class);
		addTab("act2", "猜你喜欢", UserHomeActivity.class);
		addTab("act3", "最新", LatestActivity.class);
		addTab("act4", "个人中心", UserHomeActivity.class);

	}
	

	private void addTab(String tag, String title, Class clazz) {
		TabSpec tabSpec = tabhost.newTabSpec(tag);
		tabSpec.setIndicator(title);

		Intent intent = new Intent(getApplicationContext(), clazz);
		tabSpec.setContent(intent);
		//tabSpec.setIndicator(this.getLayoutInflater().inflate(R.layout.test1, null));
		tabhost.addTab(tabSpec);
	}

	private TabHost.TabSpec buildTagSpec(String tagName, int tagLable,

	int icon, Intent content) {

		return tabhost.newTabSpec(tagName)

		.setIndicator(getResources().getString(tagLable),

		getResources().getDrawable(icon)).setContent(content);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_host, menu);
		return true;
	}

}