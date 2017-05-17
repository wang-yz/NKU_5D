package com.nkcs.friends.task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.nkcs.friends.app.MyApp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddActivityTask extends AsyncTask<String, Void, String> {

	private String url;
	private Context context;
	
	public AddActivityTask(Context context) {
		super();
		this.context = context;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "AddActivityServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	    HttpClient client = new DefaultHttpClient();
	    HttpPost request = new HttpPost(url);
	    String responseText;
		try {
	        MultipartEntity entity = new MultipartEntity();
			if(arg0[0] == null){

				return null;
			}

	    	File file = new File(arg0[0]);				
	    	FileBody fileBody = new FileBody(file);
	    	entity.addPart("file", fileBody);
	    	
	    	StringBody ActivityName = new StringBody(arg0[1], Charset.forName("utf-8"));
	    	entity.addPart("ActivityName", ActivityName);
	    	StringBody MaxNumber = new StringBody(arg0[2], Charset.forName("utf-8"));
	    	entity.addPart("MaxNumber", MaxNumber);
	    	StringBody Deadline = new StringBody(arg0[3], Charset.forName("utf-8"));
	    	entity.addPart("Deadline", Deadline);
	    	StringBody StartTime = new StringBody(arg0[4], Charset.forName("utf-8"));
	    	entity.addPart("StartTime", StartTime);	    	
	    	StringBody EndTime = new StringBody(arg0[5], Charset.forName("utf-8"));
	    	entity.addPart("EndTime", EndTime);
	    	StringBody interest = new StringBody(arg0[6], Charset.forName("utf-8"));
	    	entity.addPart("interest", interest);	    	
	    	StringBody address1 = new StringBody(arg0[7], Charset.forName("utf-8"));
	    	entity.addPart("address1", address1);
	    	StringBody address2 = new StringBody(arg0[8], Charset.forName("utf-8"));
	    	entity.addPart("address2", address2);
	    	StringBody Introduction = new StringBody(arg0[9], Charset.forName("utf-8"));
	    	entity.addPart("Introduction", Introduction);
	    	StringBody address0 = new StringBody(arg0[10], Charset.forName("utf-8"));
	    	entity.addPart("address0", address0);
	    	
	    	request.setEntity(entity);		
	    	
			HttpResponse response = client.execute(request);
			
			responseText = EntityUtils.toString(response.getEntity());
			System.out.print(responseText);
			return responseText;
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if(result.equals("成功"))
			Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
		else 
			Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
		super.onPostExecute(result);
	}

}
