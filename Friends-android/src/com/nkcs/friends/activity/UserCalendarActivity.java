package com.nkcs.friends.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.calendar.CalendarView.OnItemClickListener;
import com.nkcs.friends.calendar.*;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.task.ShowCalendarTask;
import com.nkcs.friends.R;
import com.nkcs.friends.R.layout;
import com.nkcs.friends.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserCalendarActivity extends Activity {

	private ToggleButton togBtnAttend, togBtnPost;
	private ListView lvwCalendar;
	
	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;
	
	private List<Activities> dataActivity=new ArrayList<Activities>();
	private ActivityAdapter adapterActivity;
	private String userid;
	private String calendarModel;
	private String downdate;
	private Date downDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_calendar);
		calendar=new CalendarView(this);
		
		init();
		
		format = new SimpleDateFormat("yyyy-MM-dd");
		//获取日历控件对象
		calendar = (CalendarView)findViewById(R.id.calendar);
		calendar.setSelectMore(false); //单选  
		
		calendarLeft = (ImageButton)findViewById(R.id.calendarLeft);
		calendarCenter = (TextView)findViewById(R.id.calendarCenter);
		calendarRight = (ImageButton)findViewById(R.id.calendarRight);
		try {
			//设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-"); 
		calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
		
		//获取当前选中日期
		downdate=ya[0]+"-"+ya[1];
		//downDate=calendar.getdownDate();
		
		calendarLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击上一月 同样返回年月 
				String leftYearAndmonth = calendar.clickLeftMonth(); 
				String[] ya = leftYearAndmonth.split("-"); 
				calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
			}
		});
		
		calendarRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//点击下一月
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-"); 
				calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
			}
		});
		
		//设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
		calendar.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void OnItemClick(Date selectedStartDate,Date selectedEndDate, Date downDate) {
				if(calendar.isSelectMore()){
					//月份跳转
					System.out.println("这是if");
					//Toast.makeText(getApplicationContext(), format.format(selectedStartDate)+"到"+format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
					
				}else{
					//日期进入
					UserCalendarActivity.this.downDate=downDate;
					//System.out.println("这是else、");
					// Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
					ShowCalendarTask showCalendar = new ShowCalendarTask(dataActivity, lvwCalendar, adapterActivity,downDate);
					showCalendar.execute(userid, calendarModel,downdate);
				}
			}
		});
		
	}
	
	private void init() {
		togBtnAttend = (ToggleButton) findViewById(R.id.togBtnAttend);
		togBtnPost = (ToggleButton) findViewById(R.id.togBtnPost);
		
		togBtnAttend.setOnClickListener(new onClickListenerImpl());
		togBtnPost.setOnClickListener(new onClickListenerImpl());
		
		lvwCalendar = (ListView) findViewById(R.id.lvwCalendar);
		
		// 初始状态下
		togBtnAttend.setChecked(true);
		togBtnPost.setChecked(false);
		
		try {
			this.dataActivity = new ArrayList<Activities>();
			this.adapterActivity = new ActivityAdapter(getApplicationContext(), dataActivity);

			this.lvwCalendar.setAdapter(adapterActivity);
			// this.lvwCalendar .setOnItemClickListener(new OnItemClickListenerImpl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MyApp myApp = (MyApp) getApplication();
		
		
		
		//这里是测试数据！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		//
		//userid = myApp.getUser().getUserid() + ""; //int->String
		userid=1+"";
		
		
		
		
		
		
		
		Toast.makeText(getApplicationContext(), "当前用户id: " + userid, Toast.LENGTH_SHORT).show();
		
		MyAttend();
	}
	
	private class onClickListenerImpl implements android.view.View.OnClickListener {
		@Override
		public void onClick(View arg0) {
			
			switch (arg0.getId()) {
			case R.id.togBtnAttend:
				Toast.makeText(getApplicationContext(), "显示\"我参与的\"", Toast.LENGTH_SHORT).show();
				togBtnAttend.setChecked(true);
				togBtnPost.setChecked(false);
				// lvwCalendar显示"我参与的"
				MyAttend();
				break;
			case R.id.togBtnPost:
				Toast.makeText(getApplicationContext(), "显示\"我发布的\"", Toast.LENGTH_SHORT).show();
				togBtnAttend.setChecked(false);
				togBtnPost.setChecked(true);
				// lvwCalendar显示"我发布的"
				MyPost();
				break;
			default:
				break;
			}
		}
	}
	
	private void MyAttend() {
		calendarModel = "Attend";
		ShowCalendarTask showCalendar = new ShowCalendarTask(dataActivity, lvwCalendar, adapterActivity,downDate);
		showCalendar.execute(userid, calendarModel,downdate);
	}

	private void MyPost() {
		calendarModel = "Post";
		ShowCalendarTask showCalendar = new ShowCalendarTask(dataActivity, lvwCalendar, adapterActivity,downDate);
		showCalendar.execute(userid, calendarModel,downdate);
	}
	
		
	// --------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_calendar, menu);
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