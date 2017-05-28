package com.nkcs.friends.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView imgView;
	
	public ImageLoadTask(ImageView imgView){
		this.imgView = imgView;
	}
	
	
	@Override
	protected Bitmap doInBackground(String... arg0) {

		String urlString = arg0[0];
System.out.println("task   tu  pian  =  "+urlString);
		try {
			URL url = new URL(urlString);
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
	}

}
