package com.nkcs.friends.adapter;

import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.task.FriendsPhotoTask;
import com.nkcs.friends.task.ImageLoadTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAdapter extends BaseAdapter {
	
	// 声明相关对象
	Context context;
	List<Activities> data;
	LayoutInflater layout;
	
	// 构造函数
	public ActivityAdapter(Context context, List<Activities> data) {
		this.context = context;
		this.data = data;
		this.layout = LayoutInflater.from(context);
	}

	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// 获取项目布局
		View view = arg1;
		if (view == null) {
			view = layout.inflate(R.layout.list_activity, null);
		}
		
		// 获取布局中的各组件
		ImageView imgActPictureImageView = (ImageView) view.findViewById(R.id.imgActPicture);
		TextView txtActName = (TextView) view.findViewById(R.id.txtActName);
		TextView txtStartTime = (TextView) view.findViewById(R.id.txtStartTime);
		TextView txtAddress2 = (TextView) view.findViewById(R.id.txtAddress2);

		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/FontNormal.ttf");
		
		txtActName.setTypeface(typeface);
		txtStartTime.setTypeface(typeface);
		txtAddress2.setTypeface(typeface);
		
		// 填充数据
		Activities activity = data.get(arg0);
		// imgActPicture.set...
		txtActName.setText(activity.getAct_name());
		txtStartTime.setText(activity.getAct_starttime());
		txtAddress2.setText(activity.getAct_address2());
		
		MyApp myApp = new MyApp();
		String url = myApp.getLiuURL() + "image/photo/" + activity.getAct_picture().toString();
		//String url = "http://10.0.2.2:8080/Friends/image/photo/" + activity.getAct_picture().toString();
		new ImageLoadTask(imgActPictureImageView).execute(url);
		
		return view;
	}

}
