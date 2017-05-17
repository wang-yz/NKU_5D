package com.nkcs.friends.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.R;
import com.nkcs.friends.task.FileUploadTask;
import com.nkcs.friends.task.ImageLoadTask;
import com.nkcs.friends.util.StringUtil;

public class UserHomeActivity extends Activity {

	private TextView txtUserName;
	private ImageView imgUserPhoto;
	//网址
	private Uri imageUri;
	private Dialog exitDialog;
	
	//服务器网址
	private String imageURL = "http://123.206.20.37:8080/Friends/image/photo/";
	
	private String url = "http://123.206.20.37:8080/Friends/FileUploadServlet";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		
		//获取组件
		txtUserName=(TextView) findViewById(R.id.txtUserName);
		imgUserPhoto=(ImageView) findViewById(R.id.imgUserPhoto);
		
		//显示
		showUserInfo();
		
		createDialog();
	}

	private void showUserInfo(){
		
//		MyApp myApp = (MyApp) getApplication();
//		
//		Users user=myApp.getUsers();
		
		//��ʱsssssssssssssssssssssssssssssssssssssssssssssssssssssssss
		Users user=new Users(1,"user1","111","Ů","1.gif","introduction",50,"nickname","","","1234324234");		
		
		MyApp myApp = (MyApp) getApplication();
		
		myApp.setUser(user);
		
		txtUserName.setText(user.getUsername());
		//获取头像
		imageURL += user.getPhoto();

		//--------
		new ImageLoadTask(imgUserPhoto).execute(imageURL);
	}
	//查找头像
	public void selectPhoto(View v){
		
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(intent, 1);
	}
	
	public void info(View v){
		Intent intent = new Intent(UserHomeActivity.this, UserUpdateActivity.class);
		
		startActivity(intent);
		finish();
	}
	
	
	//响应
	private class ExitOnClickImpl implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int id) {

//			if (id == DialogInterface.BUTTON_POSITIVE  && dialog.equals(exitDialog)) {
//
//				SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
//
//				Editor editor = sp.edit();
//
//				editor.remove("username");
//				editor.remove("password");
//
//				editor.commit();
//
//			}
			if (id == DialogInterface.BUTTON_POSITIVE  && dialog.equals(exitDialog)){
				System.exit(0);
			}
			
			//System.exit(0);
		}
	}
	
	//对话框
	private void createDialog(){
		
		Builder builder = new Builder(this);
		builder.setMessage("�Ƿ��˳�");

		builder.setPositiveButton("��", new ExitOnClickImpl());
		builder.setNegativeButton("��", new ExitOnClickImpl());
		
		exitDialog = builder.create();	
	}
	
	//弹出
	public void exit(View v){
		exitDialog.show();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 1){

			if(resultCode == RESULT_OK){
				
				imageUri = data.getData();

				imgUserPhoto.setImageURI(imageUri);
				
				//图片网址
				String photoPath = StringUtil.getRealPathFromURI(getApplicationContext(), imageUri);

				MyApp myApp = (MyApp) getApplication();

				//获取全局文件
				new FileUploadTask(this,myApp).execute(url, photoPath);
			}				
		}
	}
	
	// --  "我的日程" -------------------------------
	public void calendar(View v) {
		Intent intent = new Intent(UserHomeActivity.this, UserCalendarActivity.class);
		startActivity(intent);	}
	
	// --------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home, menu);
		return true;
	}

}
