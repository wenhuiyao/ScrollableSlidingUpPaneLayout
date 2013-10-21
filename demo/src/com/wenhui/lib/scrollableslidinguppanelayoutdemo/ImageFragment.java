package com.wenhui.lib.scrollableslidinguppanelayoutdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wenhui.lib.scrollableslidinguppanelayout.ScrollableSlidingUpPaneLayout;
import com.wenhui.lib.scrollableslidinguppanelayoutdemo.MainActivity.OnBackPressedListener;

public class ImageFragment extends Fragment implements LoaderCallbacks<Cursor>,
		OnItemClickListener, OnBackPressedListener {

	private ScrollableSlidingUpPaneLayout mLayout;
	private ImageAdapter mAdapter;
	private int mImageThumbnailSize;
	private int mImageThumbSpacing;
	private Uri mContentUri;
	private ImageView mFullSizeImage;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ImageAdapter(getActivity());
		mImageThumbnailSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mLayout = (ScrollableSlidingUpPaneLayout) inflater
				.inflate(R.layout.activity_main, container, false);

		final GridView gridView = (GridView) mLayout.findViewById(R.id.gridView);
		mLayout.enableOverscroll(gridView);
		// Don't show dim color
		mLayout.setCoveredFadeColor(0);
		
		mFullSizeImage = (ImageView) mLayout.findViewById(R.id.fullSizeImage);
		
		gridView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						if (mAdapter.getNumColumns() == 0) {
							final int numColumns = (int) Math.floor(gridView
									.getWidth()
									/ (mImageThumbnailSize + mImageThumbSpacing));
							if (numColumns > 0) {
								final int columnWidth = (gridView.getWidth() / numColumns)
										- mImageThumbSpacing;
								mAdapter.setNumColumns(numColumns);
								mAdapter.setItemHeight(columnWidth);
							}
						}
					}
				});
		gridView.setOnItemClickListener(this);
		gridView.setAdapter(mAdapter);
		return mLayout;
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		getLoaderManager().initLoader(100, null, this);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Cursor c = (Cursor)mAdapter.getItem(position);
		if( c != null ){
			loadImage(c);
			mLayout.collapsePane();
		}
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA, };
		String selection = "";
		String[] selectionArgs = null;
		String order = MediaStore.Images.Media._ID + " DESC";
		return new CursorLoader(getActivity(), // Parent activity context
				mContentUri, // Table to query
				projection, // Projection to return
				selection, selectionArgs, order);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		c.moveToFirst();
		loadImage(c);
		mAdapter.changeCursor(c);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.changeCursor(null);
	}

	private void loadImage(Cursor cursor) {
		int imageID = cursor.getInt(cursor
				.getColumnIndex(MediaStore.Images.Media._ID));
		Uri uri = Uri.withAppendedPath(mContentUri, Integer.toString(imageID));

		Picasso.with(getActivity()).load(uri).into(mFullSizeImage);
	}

	private class ImageAdapter extends CursorAdapter {

		private final Context mContext;
		private int mItemHeight = 0;
		private int mNumColumns = 0;
		private GridView.LayoutParams mImageViewLayoutParams;

		public ImageAdapter(Context context) {
			super(context, null, 0);
			this.mContext = context;
		}

		@Override
		public void bindView(View view, Context arg1, Cursor cursor) {
			ImageView iv = (ImageView) view;
			// Check the height matches our calculated column width
			if (iv.getLayoutParams().height != mItemHeight) {
				iv.setLayoutParams(mImageViewLayoutParams);
			}

			int imageID = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Images.Media._ID));
			Uri uri = Uri.withAppendedPath(mContentUri,
					Integer.toString(imageID));

			Picasso.with(mContext).load(uri)
					.placeholder(R.drawable.empty_photo)
					.resize(mItemHeight, mItemHeight).centerCrop().into(iv);
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			ImageView imageView = new ImageView(mContext);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(mImageViewLayoutParams);
			return imageView;
		}

		/**
		 * Sets the item height. Useful for when we know the column width so the
		 * height can be set to match.
		 * 
		 * @param height
		 */
		public void setItemHeight(int height) {
			if (height == mItemHeight) {
				return;
			}
			mItemHeight = height;
			mImageViewLayoutParams = new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, mItemHeight);
			notifyDataSetChanged();
		}

		public void setNumColumns(int numColumns) {
			mNumColumns = numColumns;
		}

		public int getNumColumns() {
			return mNumColumns;
		}

	}

	@Override
	public boolean onBackPressedCallback() {
		if( mLayout.isExpanded() ){
			mLayout.collapsePane();
			return true;
		} else {
			return false;
		}
	}

}
