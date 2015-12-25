package com.bm.projectxxx.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.bm.projectxxx.R;
import com.bm.projectxxx.adapter.LocalImageListAdapter;
import com.bm.projectxxx.bean.ImageBean;
import com.bm.projectxxx.utils.DisplayUtil;

/**
 * 显示本地图片的类
 * @author 赵成龙
 *
 */
public class LocalImageListActivity extends BaseActivity implements OnClickListener{
	private final int MediaStore_Images_Media_QUERY_TOKEN = 101;
    private AsynImageQueryHandler mQueryHandler;
    private GridView mGridView;
    private DisplayMetrics dm;
    private int mWidth = 0;
    private LocalImageListAdapter mImageListAdapter;
    private String[] projectionImages = { Media._ID, Media.DATA, Media.BUCKET_DISPLAY_NAME };
    private List<ImageBean> mImageList = new ArrayList<ImageBean>();
	
	@Override
	public int inflateContentView() {
		return R.layout.activity_local_image_list_layout;
	}

	@Override
	protected void onStart() {
		super.onStart();
		startAsyncQuery();
	}

	private void startAsyncQuery() {
		mDialogHelper.startProgressDialog();
		mQueryHandler.startQuery(MediaStore_Images_Media_QUERY_TOKEN, null,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projectionImages,
				null, null, "date_modified DESC");
	}

	@Override
	public void addListeners() {
		
	}

	@Override
	public void init() {
//		setTitleBarOverlay(true);
		setTitleText("图片列表");
		mQueryHandler = new AsynImageQueryHandler(getContentResolver());
        dm = getResources().getDisplayMetrics();
        mWidth = (dm.widthPixels - DisplayUtil.dip2px(this, 5 * 3 + 10 * 2)) / 4;
        mImageListAdapter = new LocalImageListAdapter(this, mWidth, mImageList);
        mGridView.setAdapter(mImageListAdapter);
	}

	@Override
	public void findViews() {
        mGridView = (GridView) findViewById(R.id.gv_local_image_list);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.iv_back_operate:// 返回
            finish();
            break;
		}
	}
	
	private class AsynImageQueryHandler extends AsyncQueryHandler{

		public AsynImageQueryHandler(ContentResolver contentResolver) {
			super(contentResolver);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			mDialogHelper.stopProgressDialog();
			switch (token) {
			case MediaStore_Images_Media_QUERY_TOKEN:
				initImageList(cursor);
				break;
			default:
				break;
			}
		}
	}
	
	private void initImageList(Cursor cursor) {
		if (cursor != null && cursor.getCount() > 0) {
			List<ImageBean> list = new ArrayList<ImageBean>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(Media._ID));
				String path = cursor.getString(cursor.getColumnIndex(Media.DATA));
				if (new File(path).exists()) {
					ImageBean bean = new ImageBean(id, path);
					list.add(bean);
				}
			}
			mImageList.clear();
			mImageList.addAll(list);
			mImageListAdapter.notifyDataSetChanged();
			cursor.close();
		}
	}

}
