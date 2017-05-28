package com.nkcs.friends.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nkcs.friends.R;
import com.nkcs.friends.entity.Interests;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class SecondInterestAdapter extends BaseAdapter {

	// 声明相关对象
	Context context;
	private List<String> data;
	LayoutInflater layout;
	public List<Boolean> listboolean = new ArrayList<Boolean>();

	// private LayoutInflater mInflater;
	private List<Map<String, Object>> mData;
	public static Map<Integer, Boolean> isSelected;

	
	// 构造函数
	public SecondInterestAdapter(Context context, List<String> data) {
		this.context = context;
		this.data = data;
		this.layout = LayoutInflater.from(context);
		
		// init(); // 还需要吗？
	}

	// 初始化
	private void init() {
		// 这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mData.size(); i++) {
			isSelected.put(i, false);
		}
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
			view = layout.inflate(R.layout.interest, null);
		}

		// 获取布局中的各组件
		// TextView txtInterestName = (TextView)
		// view.findViewById(R.id.txtInterestName);
		CheckBox checkBox = (CheckBox) view.findViewById(R.id.cheInterest);

		// 填充数据
		String interestsString = data.get(arg0);
		// txtInterestName.setText(interest.getInt_name());
		checkBox.setText(interestsString);
		
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/FontNormal.ttf");  
		
		checkBox.setTypeface(typeface);
		
		boolean is = checkBox.isChecked();
		if (listboolean.size() > arg0) {
			listboolean.set(arg0, is);
		} else {
			listboolean.add(is);
		}

		return view;
	}

}
