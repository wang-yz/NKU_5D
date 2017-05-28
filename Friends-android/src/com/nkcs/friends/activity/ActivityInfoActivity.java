package com.nkcs.friends.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.ImageLoadTask;
import com.nkcs.friends.task.UserAttendTask;

public class ActivityInfoActivity extends Activity {

	private ToggleButton togActivity, togUsersay;
	private ListView listInfo;
	private TextView txtActiTitle, txtUsersay;
	private ImageView imaAcPhoto;
	private Button btnAttend;

	
	private String url;
	private String responseText;
	private String imageURL;

	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_info);

		togActivity = (ToggleButton) findViewById(R.id.togActivity);
		togUsersay = (ToggleButton) findViewById(R.id.togUsersay);
		listInfo = (ListView) findViewById(R.id.listInfo);
		txtActiTitle = (TextView) findViewById(R.id.txtActiTitle);
		txtUsersay = (TextView) findViewById(R.id.txtUsersay);
		imaAcPhoto = (ImageView) findViewById(R.id.imaAcPhoto);
		btnAttend=(Button) findViewById(R.id.btnAttend);
		
		new Thread(new isExistRunnable()).start(); 
		
		MyApp myApp = new MyApp();
		url = myApp.getLiuURL() + "UserAttendServlet";
		imageURL = myApp.getLiuURL() + "image/photo/";
		System.out.println("di yi bu ="+imageURL);
		
		show();
	}

	private void show() {

		//默认
		togActivity.setBackgroundResource(R.drawable.activity_info_on_bg);
		togUsersay.setBackgroundResource(R.drawable.up_say_down_bg);
		
		MyApp myApp = (MyApp) getApplication();

		Activities activity = myApp.getActivity();

		System.out.println("transActivities.getAct_id()=" + activity.getAct_id());
		if (myApp.getActivity() != null) {
			if (activity.getAct_intro() == null) {
				txtUsersay.setText("");
			} else {
				txtUsersay.setText(activity.getAct_intro());
			}

			if (activity.getAct_name() == null) {
				txtActiTitle.setText("");
			} else {
				txtActiTitle.setText(activity.getAct_name());
			}

			if (activity.getAct_picture() == null) {
				imageURL += "1.gif"; // 默认
			} else {
				imageURL += activity.getAct_picture().toString();
			}

		    //new FriendsPhotoTask(imaAcPhoto, ActivityInfoActivity.this).execute(activity.getAct_picture().toString());

			new ImageLoadTask(imaAcPhoto).execute(imageURL);
			
			togActivity.setChecked(true);
			togUsersay.setChecked(false);

			addInfo();
			txtUsersay.setVisibility(View.GONE);

			togActivity.setOnClickListener(new onClickListenerImpl());
			togUsersay.setOnClickListener(new onClickListenerImpl());

			MyAdapter adapter = new MyAdapter();
			listInfo.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(), "当前活动无数据，请重新选择！",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private void addInfo() {
		MyApp myApp = (MyApp) getApplication();

		Activities activity = myApp.getActivity();

		Map<String, Object> item = new HashMap<String, Object>();
		if (activity.getAct_type() == null) {
			item.put("info", "活动类型" + "");
		} else {
			item.put("info", "活动类型" + activity.getAct_type());
		}
		dataList.add(item);

		Map<String, Object> item2 = new HashMap<String, Object>();
		if ((activity.getAct_value() + "") == null) {
			item2.put("info", "活动类型" + "");
		} else {
			item2.put("info", "活动热度" + activity.getAct_value());
		}
		dataList.add(item2);

		Map<String, Object> item3 = new HashMap<String, Object>();
		if ((activity.getAct_maxNumber() + "") == null) {
			item3.put("info", "参与上限" + "");
		} else {
			item3.put("info", "参与上限" + activity.getAct_maxNumber());
		}
		dataList.add(item3);

		Map<String, Object> item4 = new HashMap<String, Object>();
		if ((activity.getAct_curNumber() + "") == null) {
			item4.put("info", "当前人数" + "");
		} else {
			item4.put("info", "当前人数" + activity.getAct_curNumber());
		}
		dataList.add(item4);

		Map<String, Object> item5 = new HashMap<String, Object>();
		if ((activity.getAct_starttime() + "") == null) {
			item5.put("info", "开始时间" + "");
		} else {
			item5.put("info", "开始时间" + activity.getAct_starttime());
		}
		dataList.add(item5);

		Map<String, Object> item6 = new HashMap<String, Object>();
		if ((activity.getAct_endtime() + "") == null) {
			item6.put("info", "结束时间" + "");
		} else {
			item6.put("info", "结束时间" + activity.getAct_endtime());
		}
		dataList.add(item6);

		Map<String, Object> item7 = new HashMap<String, Object>();
		if (activity.getAct_address0() == null) {
			item7.put("info", "详细地址" + "");
		} else {
			item7.put("info", "详细地址" + activity.getAct_address0());
		}
		dataList.add(item7);

	}

	private class onClickListenerImpl implements
			android.view.View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.togActivity:
				togActivity.setChecked(true);
				togUsersay.setChecked(false);
				txtUsersay.setVisibility(View.GONE);
				listInfo.setVisibility(View.VISIBLE);
				togActivity.setBackgroundResource(R.drawable.activity_info_on_bg);
				togUsersay.setBackgroundResource(R.drawable.up_say_down_bg);
				break;
			case R.id.togUsersay:
				togActivity.setChecked(false);
				togUsersay.setChecked(true);
				listInfo.setVisibility(View.GONE);
				txtUsersay.setVisibility(View.VISIBLE);
				togActivity.setBackgroundResource(R.drawable.activity_info_down_bg);
				togUsersay.setBackgroundResource(R.drawable.up_say_on_bg);
				break;
			default:
				break;
			}
		}

	}

	public void Attend(View v) {
		MyApp myApp=(MyApp)getApplication();
		
		Activities activity=myApp.getActivity();

		System.out.println("activity.getAct_curNumber()="+activity.getAct_curNumber());
		System.out.println("activity.getAct_maxNumber()"+activity.getAct_maxNumber());
		
		if("true".equals(responseText)){
			Toast.makeText(getApplicationContext(), "你已经参与过该活动了！请勿重复参与！", Toast.LENGTH_SHORT).show();

			btnAttend.setClickable(false);
			btnAttend.setBackgroundColor(Color.rgb(207, 207, 207));
		}
		else{
		if(activity.getAct_curNumber()<activity.getAct_maxNumber()){
			//启动异步task更新数据库
			new UserAttendTask(this,myApp).execute(url);
			
			Toast.makeText(getApplicationContext(), "参与成功！", Toast.LENGTH_SHORT).show();

			btnAttend.setClickable(false);
			btnAttend.setBackgroundColor(Color.rgb(207, 207, 207));
			btnAttend.setText("已成功参与！");
		}
		else{
			Toast.makeText(getApplicationContext(), "当前参与人数已满！", Toast.LENGTH_SHORT).show();
		}
		}
	}

	class isExistRunnable implements Runnable{

		@Override
		public void run() {
			MyApp myApp=(MyApp)getApplication();
			String url = myApp.getLiuURL() + "UserAttendJudgeServlet";

			Users user=myApp.getUser();
			Activities activity=myApp.getActivity();

			try {
				HttpClient client = new DefaultHttpClient();

				HttpPost request = new HttpPost(url);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("userid", user.getUserid()+""));
				params.add(new BasicNameValuePair("activityid", activity.getAct_id()+""));

				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				HttpResponse response = client.execute(request);

				if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
					responseText = EntityUtils.toString(response.getEntity());
					//handler.sendEmptyMessage(1);
					System.out.println("responseText"+responseText);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {

			view = View.inflate(getApplicationContext(), R.layout.item_info,
					null);

			if (position % 2 == 0) {
				view.setBackgroundColor(Color.parseColor("#b3F5F7F8"));
			} else {
				view.setBackgroundColor(Color.parseColor("#e2e6e9"));
			}

			Map<String, Object> map = dataList.get(position);

			TextView txtinfo = (TextView) view.findViewById(R.id.txtActiTitle);
			txtinfo.setText(map.get("info").toString());
			return view;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_info, menu);
		return true;
	}

}
