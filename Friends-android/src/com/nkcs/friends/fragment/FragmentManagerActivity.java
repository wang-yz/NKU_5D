package com.nkcs.friends.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nkcs.friends.R;
import com.nkcs.friends.activity.PostActivity;
import com.nkcs.friends.activity.SelectInterestActivity;
import com.nkcs.friends.activity.UserCalendarActivity;
import com.nkcs.friends.activity.UserUpdateActivity;
import com.nkcs.friends.ai.AIActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.arcmenu.ArcMenu;
import com.nkcs.friends.arcmenu.ArcMenu.OnMenuItemClickListener;
import com.nkcs.friends.circularimage.CircularImage;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.FileUploadTask;
import com.nkcs.friends.task.ImageLoadTask;
import com.nkcs.friends.util.StringUtil;

/**
 * 项目的Fragment管理Activity，所有的Fragment都嵌入在这里。
 * 
 * @author JeremyLiu
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentManagerActivity extends Activity implements OnClickListener {

	private ArcMenu mArcMenu;

	// 用于展示兴趣标签的Fragment
	private InterestFragment interestFragment;

	// 用于展示推荐活动的Fragment
	private RecommendFragment recommendFragment;

	// 用于展示最新活动的Fragment
	private LatestFragment latestFragment;

	// 用于展示个人中心的Fragment
	private FriendFragment friendFragment;

	// 兴趣标签界面布局
	private View interestLayout;

	// 推荐活动界面布局
	private View recommendLayout;

	// 最新活动界面布局
	private View latestLayout;

	// 好友中心界面布局
	private View friendLayout;

	// 在Tab布局上显示兴趣标签图标的控件
	private ImageView interestImage;

	// 在Tab布局上显示推荐活动图标的控件
	private ImageView recommendImage;

	// 在Tab布局上显示最新活动图标的控件
	private ImageView latestImage;

	// 在Tab布局上显示个人中心图标的控件
	private ImageView friendImage;

	// 用于对Fragment进行管理
	private FragmentManager fragmentManager;

	private Dialog exitDialog;
	private CircularImage imgUserPhoto;
	private TextView txtUserName;
	private Button btnInfo, btnCalendar, btnInterest, btnAboutUs, btnCancellation, btnExitAll;

	private Uri imageUri;
	private String imageURL;
	private String url;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_manager);

		MyApp myApp = new MyApp();
		this.imageURL = myApp.getLiuURL() + "image/photo/";
		this.url = myApp.getLiuURL() + "FileUploadServlet";

		// 初始化布局元素
		initViews();
		
		setFont();
		
		fragmentManager = getFragmentManager();
		
		// 第一次启动时选中第0个tab
		setTabSelection(0);

		createDialog();

		showPhotoUser();

		initEvent();
	}

	private void setFont(){
		Typeface typeface = Typeface.createFromAsset(getAssets(), "font/FontNormal.ttf");
		
		txtUserName.setTypeface(typeface);
		btnInfo.setTypeface(typeface);
		btnInterest.setTypeface(typeface);
		btnAboutUs.setTypeface(typeface);
		btnCalendar.setTypeface(typeface);
		btnCancellation.setTypeface(typeface);
		btnExitAll.setTypeface(typeface);
	}
	
	private void initEvent() {
		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onClick(View view, int pos) {
				String arcSelect = (String) view.getTag();
				Toast.makeText(FragmentManagerActivity.this, "选择了: " + arcSelect, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				if (arcSelect.equals("chatroom")) {
					intent = new Intent(FragmentManagerActivity.this, FriendActivity.class);
					startActivity(intent);
				} else if (arcSelect.equals("calendar")) {
					intent = new Intent(FragmentManagerActivity.this, UserCalendarActivity.class);
					startActivity(intent);
				} else if (arcSelect.equals("post")) {
					intent = new Intent(FragmentManagerActivity.this, PostActivity.class);
					startActivity(intent);
				} else if (arcSelect.equals("location")) {
					Toast.makeText(getApplicationContext(), "暂定", Toast.LENGTH_SHORT).show();
				} else if (arcSelect.equals("ai")) {
					intent = new Intent(FragmentManagerActivity.this, AIActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "异常", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void showPhotoUser() {
		MyApp myApp = (MyApp) getApplication();

		Users user = myApp.getUser();

		txtUserName.setText(user.getUsername());

		imageURL += user.getPhoto();

		new ImageLoadTask(imgUserPhoto).execute(imageURL);
	}

	// 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	private void initViews() {
		mArcMenu = (ArcMenu) findViewById(R.id.include1);

		interestLayout = findViewById(R.id.fragment_interest_layout);
		recommendLayout = findViewById(R.id.fragment_recommend_layout);
		latestLayout = findViewById(R.id.fragment_latest_layout);
		friendLayout = findViewById(R.id.fragment_friend_layout);

		interestImage = (ImageView) findViewById(R.id.interest_image);
		recommendImage = (ImageView) findViewById(R.id.recommend_image);
		latestImage = (ImageView) findViewById(R.id.latest_image);
		friendImage = (ImageView) findViewById(R.id.user_image);

		imgUserPhoto = (CircularImage) findViewById(R.id.imgUserPhoto);
		btnInfo = (Button) findViewById(R.id.btnInfo);
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		btnCalendar = (Button) findViewById(R.id.btnCalendar);
		btnInterest = (Button) findViewById(R.id.btnInterest);
		btnAboutUs = (Button) findViewById(R.id.btnAboutUs);
		btnCancellation = (Button) findViewById(R.id.btnCancellation);
		btnExitAll = (Button) findViewById(R.id.btnExitAll);

		imgUserPhoto.setOnClickListener(new OnClickListenerImpl());
		btnInfo.setOnClickListener(new OnClickListenerImpl());
		btnCalendar.setOnClickListener(new OnClickListenerImpl());
		btnInterest.setOnClickListener(new OnClickListenerImpl());
		btnAboutUs.setOnClickListener(new OnClickListenerImpl());
		btnCancellation.setOnClickListener(new OnClickListenerImpl());
		btnExitAll.setOnClickListener(new OnClickListenerImpl());

		interestLayout.setOnClickListener(this);
		recommendLayout.setOnClickListener(this);
		latestLayout.setOnClickListener(this);
		friendLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_interest_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.fragment_recommend_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.fragment_latest_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.fragment_friend_layout:
			// 当点击了设置tab时，选中第4个tab
			// setTabSelection(3);
			Intent intent = new Intent(FragmentManagerActivity.this, FriendActivity.class);
			startActivity(intent);

			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示兴趣标签，1表示推荐活动，2表示最新活动，3表示个人中心
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了兴趣标签tab时，改变控件的图片和文字颜色
			interestImage.setImageResource(R.drawable.interest_selected);
			if (interestFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				interestFragment = new InterestFragment();
				transaction.add(R.id.content, interestFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(interestFragment);
			}
			break;
		case 1:
			// 当点击了推荐活动tab时，改变控件的图片和文字颜色
			recommendImage.setImageResource(R.drawable.recommend_selected);
			if (recommendFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				recommendFragment = new RecommendFragment();
				transaction.add(R.id.content, recommendFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(recommendFragment);
			}
			break;
		case 2:
			// 当点击了最新活动tab时，改变控件的图片和文字颜色
			latestImage.setImageResource(R.drawable.latest_selected);
			if (latestFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				latestFragment = new LatestFragment();
				transaction.add(R.id.content, latestFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(latestFragment);
			}
			break;
		case 3:
		default:
			// 当点击了个人中心tab时，改变控件的图片和文字颜色
			friendImage.setImageResource(R.drawable.user_selected);
			if (friendFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				friendFragment = new FriendFragment();
				transaction.add(R.id.content, friendFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(friendFragment);
			}
			break;
		}
		transaction.commit();
	}

	// 清除掉所有的选中状态
	private void clearSelection() {
		interestImage.setImageResource(R.drawable.interest_unselected);

		recommendImage.setImageResource(R.drawable.recommend_unselected);

		latestImage.setImageResource(R.drawable.latest_unselected);

		friendImage.setImageResource(R.drawable.user_unselected);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (interestFragment != null) {
			transaction.hide(interestFragment);
		}
		if (recommendFragment != null) {
			transaction.hide(recommendFragment);
		}
		if (latestFragment != null) {
			transaction.hide(latestFragment);
		}
		if (friendFragment != null) {
			transaction.hide(friendFragment);
		}
	}

	private class OnClickListenerImpl implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.imgUserPhoto:
				startActivityForResult(new Intent(Intent.ACTION_PICK,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
				System.out.println("更换头像");
				break;
			case R.id.btnInfo:
				startActivity(new Intent(FragmentManagerActivity.this,
						UserUpdateActivity.class));
				// getActivity().finish();
				break;
			case R.id.btnCalendar:
				startActivity(new Intent(FragmentManagerActivity.this,
						UserCalendarActivity.class));
				break;
			case R.id.btnInterest:
				startActivity(new Intent(FragmentManagerActivity.this,
						SelectInterestActivity.class));
				break;
			case R.id.btnAboutUs:
				break;
			case R.id.btnCancellation:
				break;
			case R.id.btnExitAll:
				exitDialog.show();
				break;
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {

				imageUri = data.getData();

				imgUserPhoto.setImageURI(imageUri);

				String photoPath = StringUtil.getRealPathFromURI(
						getApplicationContext(), imageUri);

				MyApp myApp = (MyApp) getApplication();

				new FileUploadTask(this, myApp).execute(url, photoPath);
			}
		}
	}

	private class ExitOnClickImpl implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int id) {

			// if (id == DialogInterface.BUTTON_POSITIVE &&
			// dialog.equals(exitDialog)) {
			//
			// //ɾ���û���Ϣ
			// SharedPreferences sp = getSharedPreferences("UserInfo",
			// MODE_PRIVATE);
			//
			// Editor editor = sp.edit();
			//
			// editor.remove("username");
			// editor.remove("password");
			//
			// editor.commit();
			//
			// }
			if (id == DialogInterface.BUTTON_POSITIVE
					&& dialog.equals(exitDialog)) {
				System.exit(0);
			}

			// System.exit(0);
		}
	}

	// �����˳��Ի���
	private void createDialog() {

		Builder builder = new Builder(this);
		builder.setMessage("确定退出？");

		builder.setPositiveButton("是", new ExitOnClickImpl());
		builder.setNegativeButton("否", new ExitOnClickImpl());

		exitDialog = builder.create();
	}

	// --------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fragment_manager, menu);
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
