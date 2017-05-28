package com.nkcs.friends.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.nkcs.friends.activity.MyFriendsActivity;
import com.nkcs.friends.app.MyApp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class FriendsPhotoTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imgView;
	private Activity context;
	private String imageURL;
	
	public FriendsPhotoTask(ImageView imgView, Activity context){
		this.imgView = imgView;
		this.context = context;
		
		MyApp myApp = new MyApp();
		this.imageURL = myApp.getLiuURL() + "image/photo/";
	}
	
	
	@Override
	protected Bitmap doInBackground(String... arg0) {

		String urlString = arg0[0];

		try {
			URL url = new URL(imageURL + urlString);
			InputStream is = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		if(result != null){
			imgView.setImageBitmap(result);
		}
		System.out.println("22222");
	}

}
