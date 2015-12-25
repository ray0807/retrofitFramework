package com.bm.projectxxx.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bm.projectxxx.R;
import com.bm.projectxxx.utils.ViewHolder;
import com.bm.projectxxx.views.CustomSquareImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * 显示图片的适配器
 *
 * @author 赵成龙
 */
public class FrescoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;
    private int mWidth = 0;

    public FrescoListAdapter(Context context, int width,
                             List<String> mImageList) {
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

        final String imageUrl = mList.get(position);

        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                        .setResizeOptions(
                                new ResizeOptions(mWidth, mWidth))
                        // 不显示图片渐进式的过程
                        .setProgressiveRenderingEnabled(false)
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setAutoPlayAnimations(true)
                .build();
        imageView.setController(draweeController);
        return convertView;
    }

}
