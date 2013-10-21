package com.wenhui.lib.scrollableslidinguppanelayoutdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	
	private OnBackPressedListener mBackPressedListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageFragment fragment;
		if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
			final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			fragment = new ImageFragment();
			mBackPressedListener = fragment;
			ft.add(android.R.id.content, fragment, TAG);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if( mBackPressedListener != null && !mBackPressedListener.onBackPressedCallback() ){
			super.onBackPressed();
		}
	}



	public static interface OnBackPressedListener {
		public boolean onBackPressedCallback();
	}
	
}
