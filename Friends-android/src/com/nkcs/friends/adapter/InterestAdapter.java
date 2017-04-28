package com.nkcs.friends.adapter;

import java.util.List;

import com.nkcs.friends.R;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.task.ImageLoadTask;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class InterestAdapter extends BaseAdapter {
	
	// 声明相关对象
	Context context;
	List<Interests> data;
	LayoutInflater layout;
	
	// 构造函数
	public InterestAdapter(Context context, List<Interests> data) {
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
			view = layout.inflate(R.layout.list_interest, null);
		}
		
		// 获取布局中的各组件
		TextView txtInterestFirst = (TextView) view.findViewById(R.id.txtInterestFirst);
		ImageView imgInterest = (ImageView) view.findViewById(R.id.imgInterest);
		// 填充数据
		Interests interest = data.get(arg0);
		txtInterestFirst.setText(interest.getInt_name());

		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/FontNormal.ttf");  
		
		txtInterestFirst.setTypeface(typeface);
		
		MyApp myApp = new MyApp();
		String url = myApp.getLiuURL() + "image/photo/" + interest.getInt_image().toString();
		new ImageLoadTask(imgInterest).execute(url);

		return view;
	}

}
