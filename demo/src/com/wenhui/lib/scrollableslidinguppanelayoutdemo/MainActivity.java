package com.wenhui.lib.scrollableslidinguppanelayoutdemo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;

public class MainActivity extends FragmentActivity {

	private static final String TAG = "MainActivity";
	
	private OnBackPressedListener mBackPressedListener;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( Build.VERSION.SDK_INT > 10 ){
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
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
		super.onBackPressed();
	}



	public static interface OnBackPressedListener {
		public boolean onBackPressedCallback();
	}
	
}
