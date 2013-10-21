package com.wenhui.lib.scrollableslidinguppanelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPaneLayout.PanelExpandedListener;

public class ScrollableSlidingUpPaneLayoutHelper implements View.OnTouchListener {

	private int mTouchSlop;
	private int mInitialY = 0;
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

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (mViewDelegate.isReadyForPull(v, event.getX(), event.getY())) {
				mInitialY = (int) event.getY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mInitialY > 0) {
				int yDiff = mInitialY - (int) event.getY();

				if (yDiff < mTouchSlop) {
					mTargetView.requestDisallowInterceptTouchEvent(true);
					mSlidingLayout.setSlidingEnabled(true);
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mInitialY = 0;
		}

		return false;
	}
	
	private ScrollableSlidingUpPaneLayout.PanelExpandedListener mPanelExpandedListener = new PanelExpandedListener() {
		
		@Override
		public void onPanelExpanded() {
			mSlidingLayout.setSlidingEnabled(false);
		}
		
		@Override
		public void onPanelCollapse() {
			mSlidingLayout.setSlidingEnabled(true);
		}
	};
	
	
	/**
	 * ViewDelegates are what are used to de-couple the Attacher from the different types of
     * scrollable views.
	 */
	public static abstract class ViewDelegate {

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
		public abstract boolean isReadyForPull(View view, float x, float y);
	}
	
}
