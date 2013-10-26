package com.wenhui.lib.scrollableslidinguppanelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPaneLayout.PanelExpandedListener;

public class ScrollableSlidingUpPaneLayoutHelper implements View.OnTouchListener {

	private int mTouchSlop;
	private int mInitialY = -1;
	private ViewDelegate mViewDelegate;
	private ViewGroup mTargetView;
	private ScrollableSlidingUpPaneLayout mSlidingLayout;
	
	static ScrollableSlidingUpPaneLayoutHelper getInstance(Context context){
		return new ScrollableSlidingUpPaneLayoutHelper(context);
	}
	
	protected ScrollableSlidingUpPaneLayoutHelper(Context context){
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	public void addScrollableView(ScrollableSlidingUpPaneLayout layout, ViewGroup view){
		mTargetView = view;
		mViewDelegate = InstanceCreationUtils.getBuiltInViewDelegate(mTargetView);
		if (mViewDelegate == null) {
			throw new IllegalArgumentException(
					"No view handler found. Please provide one.");
		}
		
		mTargetView.setOnTouchListener(this);
		
		mSlidingLayout = layout;
		mSlidingLayout.setPanelExpandedListener(mPanelExpandedListener);
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final int action = MotionEventCompat.getActionMasked(event);
		Log.d("Helper", "Receive touch event action " + action + ", y position: " + event.getY());
		
		if( mTargetView instanceof SlideableScrollView ){
			mInitialY = ( (SlideableScrollView)mTargetView ).mInitialY;
		}
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.d("Helper", "action down");
			if (mViewDelegate.isReadyForPull(v, event.getX(), event.getY())) {
				Log.d("Helper", "Set y initial position");
				mInitialY = (int) event.getY();
			}

			break;
		case MotionEvent.ACTION_MOVE:
			
			if (mInitialY > 0) {
				int yDiff = mInitialY - (int) event.getY();
				Log.d("Helper", "Move position: " + yDiff);
				
				if ( yDiff < mTouchSlop ) {
					enableSlidingPane();
					resetTouch();
				} 
			} 
			
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			resetTouch();
		}

		return false;
	}
	
	private void resetTouch() {
		mInitialY = -1;
	}
	
	private void enableSlidingPane() {
		mTargetView.requestDisallowInterceptTouchEvent(true);
		mSlidingLayout.setSlidingEnabled(true);
		mSlidingLayout.requestDisallowInterceptTouchEvent(false);
	}
	
	private ScrollableSlidingUpPaneLayout.PanelExpandedListener mPanelExpandedListener = new PanelExpandedListener() {
		
		@Override
		public void onPanelExpanded() {
			mSlidingLayout.setSlidingEnabled(false);
		}
		
		@Override
		public void onPanelCollapse() {
			enableSlidingPane();
		}

		@Override
		public void onPanelHalfExpanded() {
//			mSlidingLayout.setSlidingEnabled(false);
//			mSlidingLayout.requestDisallowInterceptTouchEvent(true);
		}
	};
	
	/**
	 * ViewDelegates are what are used to de-couple the Attacher from the different types of
     * scrollable views.
	 */
	public static interface ViewDelegate {

		/**
		 * Allows you to provide support for View which do not have built-in
		 * support. In this method you should cast <code>view</code> to it's
		 * native class, and check if it is scrolled to the top.
		 *
		 * @param view
		 *            The view which has should be checked against.
         * @param x The X co-ordinate of the touch event
         * @param y The Y co-ordinate of the touch event
		 * @return true if <code>view</code> is scrolled to the top.
		 */
		public boolean isReadyForPull(View view, float x, float y);
	}
	
}
