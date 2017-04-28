package com.nkcs.friends.fragment;

import com.nkcs.friends.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class FriendFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View friendLayout = inflater.inflate(R.layout.fragment_friend_layout,
				container, false);

		return friendLayout;
	}

}
