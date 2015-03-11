package com.wenhui.lib.scrollableslidinguppanelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * ScrollView that is for working with ScrollableSlidingUpPaneLayout
 * @author wenhuiyao
 *
 */
public class SlideableScrollView extends ScrollView implements ScrollableSlidingUpPaneLayoutHelper.ViewDelegate{

	final static String TAG = "SlideableScrollView";
	
	private boolean isReadyToPull = false;
	int mInitialY = -1;
	private int mVerticalScrollOrigin = -1;
	
	public SlideableScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public SlideableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideableScrollView(Context context) {
		super(context);
	}
	
	
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
		return isReadyToPull && mVerticalScrollOrigin <= 0;
	}
	
	@Override
	protected void onScrollChanged (int l, int t, int oldl, int oldt){
		mVerticalScrollOrigin = t;
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
