package com.nkcs.friends.activity;

import com.mrwujay.cascade.activity.MapActivity;
import com.nkcs.friends.R;
import com.nkcs.friends.task.AddActivityTask;
import com.nkcs.friends.util.PathUtil;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostActivity extends Activity {

	private EditText edtActivityName, edtMaxNumber, edtActContent;
	private TextView txtDeadlineInput, txtStartTimeInput, txtEndTimeInput;
	private TextView txtInterest;
	private TextView txtActAddress;
	private ImageView imgActivity;
	
	private String ActivityName, Introduction;
	private String MaxNumber;
	private String Deadline, StartTime, EndTime;
	private String interest = "所有";
	private String address0 = "天津市", address1 = "天津市", address2 = "和平区";
	
	private String realPath = null;
	private Uri uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		edtActivityName = (EditText) findViewById(R.id.edtActivityName);
		edtMaxNumber = (EditText) findViewById(R.id.edtMaxNumber);
		txtDeadlineInput = (TextView) findViewById(R.id.txtDeadlineInput);
		txtStartTimeInput = (TextView) findViewById(R.id.txtStartTimeInput);
		txtEndTimeInput = (TextView) findViewById(R.id.txtEndTimeInput);
		edtActContent = (EditText) findViewById(R.id.edtActContent);
		txtInterest = (TextView) findViewById(R.id.txtInterest);
		txtActAddress = (TextView) findViewById(R.id.txtActAddress);
		imgActivity = (ImageView) findViewById(R.id.imgActivity);
		
		txtInterest.setOnClickListener(new OnClickListenerImpl());
		txtDeadlineInput.setOnClickListener(new OnClickListenerImpl());
		txtStartTimeInput.setOnClickListener(new OnClickListenerImpl());
		txtEndTimeInput.setOnClickListener(new OnClickListenerImpl());
		txtActAddress.setOnClickListener(new OnClickListenerImpl());
		imgActivity.setOnClickListener(new OnClickListenerImpl());
		
	}

	
	private class OnClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.txtInterest:
//				Intent intent = new Intent(PostActivity.this, );
//				startActivityForResult(intent, 1);
				break;
			case R.id.imgActivity:
		        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        startActivityForResult(intent1, 2);
				break;
			case R.id.txtDeadlineInput:
				Intent intent2 = new Intent(PostActivity.this, DateTimeActivity.class);
				startActivityForResult(intent2, 3);
				break;
			case R.id.txtStartTimeInput:
				Intent intent3 = new Intent(PostActivity.this, DateTimeActivity.class);
				startActivityForResult(intent3, 4);
				break;
			case R.id.txtEndTimeInput:
				Intent intent4 = new Intent(PostActivity.this, DateTimeActivity.class);
				startActivityForResult(intent4, 5);
				break;
			case R.id.txtActAddress:
				Intent intent5 = new Intent(PostActivity.this, MapActivity.class);
				startActivityForResult(intent5, 6);
				break;	
			default:
				break;
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				//��ȡ����ֵ����ʾ
				
			}else {
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		
		if(requestCode == 2){
			if(resultCode == RESULT_OK){
				uri = data.getData();
				imgActivity.setImageURI(uri);
		        realPath = PathUtil.getRealPathFromURI(getApplicationContext(), uri);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		
		if(requestCode == 3){
			if(resultCode == RESULT_OK){
				Deadline = data.getStringExtra("datetime").toString();
				txtDeadlineInput.setText(Deadline);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == 4){
			if(resultCode == RESULT_OK){
				StartTime = data.getStringExtra("datetime").toString();
				txtStartTimeInput.setText(StartTime);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == 5){
			if(resultCode == RESULT_OK){
				EndTime = data.getStringExtra("datetime").toString();
				txtEndTimeInput.setText(EndTime);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == 6){
			if(resultCode == RESULT_OK){
				address0 = data.getStringExtra("address0").toString();
				address1 = data.getStringExtra("address1").toString();
				address2 = data.getStringExtra("address2").toString();
				txtActAddress.setText(address0 + address1 + address2);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	private void getInformation(){
		ActivityName = edtActivityName.getText().toString();
		MaxNumber = edtMaxNumber.getText().toString();
		Deadline = txtDeadlineInput.getText().toString();
		StartTime = txtStartTimeInput.getText().toString();
		EndTime = txtEndTimeInput.getText().toString();	
		Introduction = edtActContent.getText().toString();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu_post, menu);
		return true;
	}
	
	private void addActivity(){
		new AddActivityTask(this).execute(realPath, ActivityName, MaxNumber, 
				Deadline, StartTime, EndTime, interest, address1, address2, Introduction, address0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub		
		switch (item.getItemId()) {
		case R.id.ab_confirm:
			getInformation();
			if(realPath == null || ActivityName == null || MaxNumber == null || 
				Deadline == null || StartTime == null || EndTime == null ||
				interest == null || address1 == null || address2 == null || 
				Introduction == null || address0 == null){
				Toast.makeText(getApplicationContext(), "Please input info", Toast.LENGTH_SHORT).show();
			}else {
				addActivity();
			}
			break;

		case R.id.ab_cancle:
//			Intent intent = new Intent(PostActivity.this, MainActivity.class);
//			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		
		return true;
	}
	
}
