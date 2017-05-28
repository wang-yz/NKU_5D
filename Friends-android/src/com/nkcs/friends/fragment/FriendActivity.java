package com.nkcs.friends.fragment;


import java.util.ArrayList;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.R.id;
import com.nkcs.friends.R.layout;
import com.nkcs.friends.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class FriendActivity extends FragmentActivity {
	
    private ViewPager vpgFriendFragment;	// 用于存放选项标题的集合 
    private List<Fragment> lstFragments;	// 用于存放二级Fragment窗体的集合
    private List<String> lstTitles;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		
		this.vpgFriendFragment = (ViewPager) findViewById(R.id.vpgFriendActivity);

	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		
		// 创建Fragment窗体对象
		List_FriendFragment ffList = new List_FriendFragment();
		Recommend_FriendFragment ffRecommend = new Recommend_FriendFragment();
		Chatroom_FriendFragment ffChatroom = new Chatroom_FriendFragment();
		
		// 将窗体对象添加到集合lstFragments
		this.lstFragments = new ArrayList<Fragment>();
		this.lstFragments.add(ffList);
		this.lstFragments.add(ffRecommend);
		this.lstFragments.add(ffChatroom);
		
		// 实例化lstTitles集合
		this.lstTitles = new ArrayList<String>();
		this.lstTitles.add("好友列表");
		this.lstTitles.add("好友推荐");
		this.lstTitles.add("聊天室列表");
        
        // 创建适配器Fragment窗体集合和选项标题集合绑定装载 (自定义) (getSupportFragmentManager)
		AppsViewPagerAdapter adapter = new AppsViewPagerAdapter(getSupportFragmentManager(), lstFragments, lstTitles);
		
		// ViewPager对象绑定适配器
		this.vpgFriendFragment.setAdapter(adapter);
	}


}
