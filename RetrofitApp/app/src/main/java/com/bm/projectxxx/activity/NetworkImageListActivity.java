package com.bm.projectxxx.activity;

import android.util.DisplayMetrics;
import android.widget.GridView;

import com.bm.projectxxx.R;
import com.bm.projectxxx.adapter.FrescoListAdapter;
import com.bm.projectxxx.utils.DisplayUtil;
import com.bm.projectxxx.utils.Images;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示网络图片
 * @author 赵成龙
 *
 */
public class NetworkImageListActivity extends BaseActivity {
    private GridView mGridView;
    private DisplayMetrics dm;
    private int mWidth = 0;
    private FrescoListAdapter mImageListAdapter;
	private List<String> listData = new ArrayList<String>();

    @Override
    public int inflateContentView() {
        return R.layout.activity_local_image_list_layout;
    }

    @Override
    public void findViews() {
        mGridView = (GridView) findViewById(R.id.gv_local_image_list);
    }

	@Override
	public void init() {
		setTitleText("网络图片列表");
        dm = getResources().getDisplayMetrics();
        mWidth = (dm.widthPixels - DisplayUtil.dip2px(this, 5 * 3 + 10 * 2)) / 4;
        mImageListAdapter = new FrescoListAdapter(this, mWidth, listData);
        mGridView.setAdapter(mImageListAdapter);

        getImageUrls();
	}

    @Override
    public void addListeners() {

    }

    private void getImageUrls() {
        int count = Images.imageUrls.length;
        for (int i = 0; i < count; i ++) {
            listData.add(Images.imageUrls[i]);
        }

        mImageListAdapter.notifyDataSetChanged();
    }
	
}
