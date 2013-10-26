package com.wenhui.lib.scrollableslidinguppanelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class SlideableScrollView extends ScrollView implements ScrollableSlidingUpPaneLayoutHelper.ViewDelegate{

	final static String TAG = "SlideableScrollView";
	
	private int mScrollY = 100;
	private boolean isReadyToPull = false;
	
	public SlideableScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SlideableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SlideableScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	int mInitialY = -1;
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		final int action = MotionEventCompat.getActionMasked(event);
		
		if( action == MotionEvent.ACTION_DOWN && getScrollY() <= 0 ){
			mInitialY = (int) event.getY();
			isReadyToPull = true;
			
		}
		
		return super.onInterceptTouchEvent(event);
	}
	
	
	@Override
	public boolean isReadyForPull(View view, float x, float y) {
		Log.d("ScrollView", "isReadyForPull: " + mScrollY );
		return isReadyToPull;
	}

}
