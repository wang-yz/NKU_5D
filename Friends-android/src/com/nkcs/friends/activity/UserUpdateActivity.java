package com.nkcs.friends.activity;

import com.mrwujay.cascade.activity.MapActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.FragmentManagerActivity;
import com.nkcs.friends.R;
import com.nkcs.friends.task.UserUpdateTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserUpdateActivity extends Activity {

	private EditText edtNickname, edtPassword, edtPhonenumber, edtIntro;
	private RadioGroup rdoGender;
	private RadioButton rdoMan, rdoWomen;
	private TextView txtAddressInput;
	private Button btnConfirm, btnCancle;
	private ProgressDialog userUpdateDialog;
	
	private String nickname, password, phonenumber, introduction, gender, username, oldPassword,
	               address0, address1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_update);
		
		edtNickname = (EditText) findViewById(R.id.edtNickname);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		edtPhonenumber = (EditText) findViewById(R.id.edtPhonenumber);
		edtIntro = (EditText) findViewById(R.id.edtIntro);
		rdoGender = (RadioGroup) findViewById(R.id.rdoGender);
		rdoMan = (RadioButton) findViewById(R.id.rdoMan);
		rdoWomen = (RadioButton) findViewById(R.id.rdoWomen);
		txtAddressInput= (TextView) findViewById(R.id.txtAddressInput);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnCancle = (Button) findViewById(R.id.btnCancle);
		
		setInformation();
		
		btnConfirm.setOnClickListener(new OnClickListenerImpl());
		btnCancle.setOnClickListener(new OnClickListenerImpl());
		rdoGender.setOnCheckedChangeListener(new OnCheckedChangeListenerImpl());
		txtAddressInput.setOnClickListener(new OnClickListenerImpl());
		
		userUpdateDialog = new ProgressDialog(this);
		userUpdateDialog.setMessage("正在获取当前信息...");
	}

	private void setInformation(){
		
		MyApp myApp = (MyApp) this.getApplication();
		Users users = myApp.getUser();

		oldPassword = users.getPassword();
		
		username = users.getUsername();
		if (users.getNickname().toString() == null) {
			edtNickname.setText("");
		} else {
			edtNickname.setText(users.getNickname().toString());
		}
		
		if (users.getPassword().toString() == null) {
			edtPassword.setText("");
		} else {
			edtPassword.setText(users.getPassword().toString());
		}
		
		if (users.getPhonenumber().toString() == null) {
			edtPhonenumber.setText("");
		} else {
			edtPhonenumber.setText(users.getPhonenumber().toString());
		}
		
		if (users.getIntroduction().toString() == null) {
			edtIntro.setText("");
		} else {
			edtIntro.setText(users.getIntroduction().toString());
		}
		
		if("女".equals(users.getGender().toString())){
			rdoMan.setChecked(true);
		}else{
			rdoWomen.setChecked(true);
		}
		
		if(users.getAddress1().toString() == null){
			txtAddressInput.setText("");
		}else {
			txtAddressInput.setText(users.getAddress1().toString() + users.getAddress2().toString());
		}
	}
	
	private void getInformation(){
		nickname = edtNickname.getText().toString();
		password = edtPassword.getText().toString();
		phonenumber = edtPhonenumber.getText().toString();
		introduction = edtIntro.getText().toString();
	}
	
	private class OnCheckedChangeListenerImpl implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case R.id.rdoMan:
				gender = "男";
				break;

			case R.id.rdoWomen:
				gender = "女";
				break;
				
			default:
				break;
			}
		} 
		
	}
	
	private class OnClickListenerImpl implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnConfirm:
				getInformation();
				userUpdateDialog.show();
				MyApp myApp = (MyApp) getApplicationContext();
				new UserUpdateTask(UserUpdateActivity.this, userUpdateDialog, myApp)
				.execute(nickname, password, phonenumber, introduction, gender, username, oldPassword, address0, address1);
				break;
				
			case R.id.btnCancle:
				UserUpdateActivity.this.finish();
				Intent intent = new Intent(UserUpdateActivity.this, FragmentManagerActivity.class);
				startActivity(intent);
				break;
				
			case R.id.txtAddressInput:
				Intent intent2 = new Intent(UserUpdateActivity.this, MapActivity.class);
				startActivityForResult(intent2, 1);
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
				address0 = data.getStringExtra("address0").toString();
				address1 = data.getStringExtra("address1").toString();
				txtAddressInput.setText(address0 + address1);
			}else{
				Toast.makeText(getApplicationContext(), "取消选取", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_update, menu);
		return true;
	}

}
