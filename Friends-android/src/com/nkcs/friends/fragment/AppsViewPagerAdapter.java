package com.nkcs.friends.fragment;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;

public class AppsViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> lstFragments;
	private List<String> lstTitles;

	public AppsViewPagerAdapter(FragmentManager fm, List<Fragment> lstFragments, List<String> lstTitles) {
		super(fm);
		this.lstFragments = lstFragments;
		this.lstTitles = lstTitles;
	}
	
	
	@Override
	public Fragment getItem(int position) {
		return (this.lstFragments == null || this.lstFragments.size() == 0) ? null : this.lstFragments.get(position);
	}

	@Override
	public int getCount() {
		return (this.lstFragments == null || this.lstFragments.size() == 0) ? 0 : this.lstFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return (this.lstTitles.size() > position) ? this.lstTitles.get(position) : "";
	}
	
}
