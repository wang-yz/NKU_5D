package com.nkcs.friends.task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;

public class FileUploadTask extends AsyncTask<String, Void, String> {

	private Context context;
	private MyApp myApp;
	
	public FileUploadTask(Context context, MyApp myApp){
		this.context = context;
		this.myApp=myApp;
	}
	
	@Override
	protected String doInBackground(String... parmas) {

		String url = parmas[0];
		String photoPath = parmas[1];
		
		try {
			
			HttpClient client = new DefaultHttpClient();
			
			HttpPost request = new HttpPost(url);
			
	        MultipartEntity entity = new MultipartEntity();
			
	    	File file = new File(photoPath);				
	    	FileBody fileBody = new FileBody(file);
	    	entity.addPart("file", fileBody);
	    	
	    	
//	    	MyApp myApp = (MyApp) context.getApplicationContext();
//
//	    	Users user=myApp.getUser();


			Users user=new Users(1,"user1","111","用户","1.gif","",50,"","","","1234324234");	
	    	
	    	StringBody username = new StringBody(user.getUsername(), Charset.forName("utf-8"));
	    	entity.addPart("username", username);
	    	
	    	StringBody password = new StringBody(user.getPassword(), Charset.forName("utf-8"));
	    	entity.addPart("password", password);
	    	
	    	request.setEntity(entity);		
	    	
			HttpResponse response = client.execute(request);
						
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				
				String responseText = EntityUtils.toString(response.getEntity());
				
				return responseText;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		
		Gson gson = new Gson();
		Users user = gson.fromJson(result, Users.class);
		if(user!=null){
			myApp.setUser(user);
			Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
		}
			
		
		super.onPostExecute(result);
	}

}