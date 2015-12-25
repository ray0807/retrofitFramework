package com.bm.projectxxx.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bm.projectxxx.R;
import com.bm.projectxxx.bean.ImageBean;
import com.bm.projectxxx.utils.ViewHolder;
import com.bm.projectxxx.views.CustomSquareImageView;
import com.squareup.picasso.Picasso;

/**
 * 显示本地图片的适配器
 * @author 赵成龙
 * 
 */
public class LocalImageListAdapter extends BaseAdapter {
	private Context mContext;
	private List<ImageBean> mList;
	private LayoutInflater mInflater;
	private int mWidth = 0;

	public LocalImageListAdapter(Context context, int width,
			List<ImageBean> mImageList) {
		mContext = context;
		mWidth = width;
		mInflater = LayoutInflater.from(mContext);
		mList = mImageList;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.common_pic_gridview_item,
					null);
		}
		final CustomSquareImageView imageView = ViewHolder.get(convertView,
				R.id.iv_wish_helping_pic_gridview_item);

		final ImageBean bean = mList.get(position);
		final String path = bean.data;

		// Picasso.with(mContext).setIndicatorsEnabled(true); // debug source
		Picasso.with(mContext).load(new File(path)).resize(mWidth, mWidth)
				.centerCrop().placeholder(R.drawable.default_icon)
				.into(imageView);

		return convertView;
	}

}
