package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.adapter.SecondInterestAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.FragmentManagerActivity;
import com.nkcs.friends.task.SelectBackTask;
import com.nkcs.friends.task.SelectInterestTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class SelectInterestActivity extends Activity {

	private List<String> data;
	private SecondInterestAdapter adapter;
	private ListView listView;

	public MyApp myApp;
	private int userid;
	private static Dialog dlgExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_interest);
		listView = (ListView) findViewById(R.id.listViewInterest);

		this.data = new ArrayList<String>(); // 总容器
		this.adapter = new SecondInterestAdapter(getApplicationContext(), data);
		this.listView.setAdapter(adapter);
		
		
		myApp = (MyApp) getApplicationContext();
		Users user = myApp.getUser();
		userid=user.getUserid();

		showInterestFirst();
		// selectInterest();

		Builder builder = new Builder(this);		
		builder.setTitle("haha");
		builder.setMessage("是否确认选择");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("是", new ExitOnClickListenerImpl());
		builder.setNegativeButton("否", null);	
		this.dlgExit = builder.create();
	}
	
	private class ExitOnClickListenerImpl implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int id) {

			if(id==Dialog.BUTTON_POSITIVE){
				
				Intent intent = new Intent(SelectInterestActivity.this, FragmentManagerActivity.class);
				new SelectBackTask(SelectInterestActivity.this, adapter, data, intent,myApp).execute(userid+"");
			}
		}		
	}

	public void showInterestFirst() {
		SelectInterestTask showInterestFirst = new SelectInterestTask(this,
				data, listView, adapter);
		showInterestFirst.execute("second");

	}

	public void OK(View v) {
		// listView
		this.adapter.notifyDataSetChanged();
		SelectInterestActivity.dlgExit.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_interest, menu);
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
