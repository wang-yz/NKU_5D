package com.nkcs.friends.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nkcs.friends.R;
import com.nkcs.friends.adapter.InterestAdapter;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.slidemenu.XCSlideMenu;
import com.nkcs.friends.task.ShowInterestTask;

public class MainActivity extends Activity {
	
	private ListView lvwShow;
	private int status = 1; // 用于表明当前页面显示状态: 1.一级标签 2.二级标签
	private List<Interests> dataInterest;
	private InterestAdapter adapterInterest;
    private XCSlideMenu xcSlideMenu;
    private TextView btnSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	      //requestWindowFeature(Window.FEATURE_NO_TITLE);
//	      xcSlideMenu = (XCSlideMenu) findViewById(R.id.slideMenu);
//	      btnSwitch = (TextView)findViewById(R.id.btnSwitch);
//	      btnSwitch.setClickable(true);
//	      btnSwitch.setOnClickListener(new OnClickListener() {
//	          
//	          public void onClick(View v) {
//	              // TODO Auto-generated method stub
//	              xcSlideMenu.switchMenu();
//	          }
//	      });
		
		this.lvwShow = (ListView) findViewById(R.id.lvwShow);
		this.dataInterest = new ArrayList<Interests>();
		this.adapterInterest = new InterestAdapter(getApplicationContext(), dataInterest);
		
		this.lvwShow.setAdapter(adapterInterest);
		this.lvwShow.setOnItemClickListener(new OnItemClickListenerImpl());
		
		showInterestFirst();
	}
	
	public void showInterestFirst() {
		//ShowInterestTask showInterestFirst = new ShowInterestTask(dataInterest, lvwShow, adapterInterest);
		//showInterestFirst.execute("first");
		status = 2;
	}
	
	private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String parentName = dataInterest.get(position).getInt_name();
			// Toast.makeText(getApplicationContext(), parentName, Toast.LENGTH_SHORT).show();
			switch(status) {
			case 2: {
				//ShowInterestTask showInterestSecond = new ShowInterestTask(dataInterest, lvwShow, adapterInterest);
				//showInterestSecond.execute(parentName);
				status= 3;
				break;
			}
			case 3: {
				// 跳转到兴趣活动界面
				Intent intent = new Intent(MainActivity.this, ActivityActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("activityShowModel", "interest");
				bundle.putString("int_name", parentName);
				intent.putExtras(bundle);
				
				// Toast.makeText(getApplicationContext(), "选择了: " + parentName, Toast.LENGTH_SHORT).show();

				MainActivity.this.startActivity(intent);
				// 关闭当前页面
				MainActivity.this.finish();
				break;
			}
			default: 
				break;
			}
			System.out.println(status);
		}
		
	}
	
	
	


	// ---------------------------------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
