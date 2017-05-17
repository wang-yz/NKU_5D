package com.nkcs.friends.activity;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.task.ImageLoadTask;
import com.nkcs.friends.task.NotmoreTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivitiesActivity extends Activity {

	private TextView txtNotifiName, txtNotifiAdd, txtNotifiDeadTime, 
	txtNotifiStartTime, txtNotifiNum, txtNotifiIntro;
	private String name, address, deadline, starttime, curNumber, intro, picture;
	private ImageView imgActivityNotification;
	private String imageURL = "http://123.206.20.37:8080/Friends/image/photo/";
	private CheckBox ckbNotmore;
	private int activityid, userid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_activities);
		
		txtNotifiName = (TextView) findViewById(R.id.txtNotifiName);
		txtNotifiAdd = (TextView) findViewById(R.id.txtNotifiAdd);
		txtNotifiDeadTime = (TextView) findViewById(R.id.txtNotifiDeadTime);
		txtNotifiStartTime = (TextView) findViewById(R.id.txtNotifiStartTime);
		txtNotifiNum = (TextView) findViewById(R.id.txtNotifiNum);
		txtNotifiIntro = (TextView) findViewById(R.id.txtNotifiIntro);
		imgActivityNotification = (ImageView) findViewById(R.id.imgActivityNotification);
		ckbNotmore = (CheckBox) findViewById(R.id.ckbNotmore);
		
		MyApp myApp = (MyApp) getApplication();
		Users users = myApp.getUser();
		userid = users.getUserid();
		
		Intent intent = this.getIntent();
		activityid = intent.getIntExtra("id", 0);
		name = intent.getStringExtra("name");
		address = intent.getStringExtra("address");
		deadline = intent.getStringExtra("deadline");
		starttime = intent.getStringExtra("starttime");
		curNumber = intent.getStringExtra("curNumber");
		intro = intent.getStringExtra("intro");
		picture = intent.getStringExtra("picture");
		
		txtNotifiName.setText(name);
		txtNotifiAdd.setText(address);
		txtNotifiDeadTime.setText(deadline);
		txtNotifiStartTime.setText(starttime);
		txtNotifiNum.setText(curNumber);
		txtNotifiIntro.setText(intro);
		new ImageLoadTask(imgActivityNotification).execute(imageURL + picture);		
	}

	public void seemore(View v){
		if(ckbNotmore.isChecked()){
			new NotmoreTask(this).execute(userid + "", activityid + "");
		}
		Intent intent = new Intent(getApplicationContext(), ActivityActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification_activities, menu);
		return true;
	}

}
