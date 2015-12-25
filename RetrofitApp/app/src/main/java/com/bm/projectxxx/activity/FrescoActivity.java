package com.bm.projectxxx.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import com.bm.projectxxx.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

/**
 * Created by zhaocl on 2015/5/21.
 */
public class FrescoActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDraweeView simpleDraweeView;

    private SimpleDraweeView pregressiveView;

    @Override
    public int inflateContentView() {
        return R.layout.activity_fresco_layout;
    }

    @Override
    public void findViews() {
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_view);
        pregressiveView = (SimpleDraweeView) findViewById(R.id.sdv_view_pregressive);
    }

    @Override
    public void init() {
        setTitleText("Fresco框架");
        setRightOperateIcon(R.mipmap.general_add_icon);
        // 简单使用
        simpleDraweeView.setImageURI(Uri.parse("http://p3.so.qhimg.com/t01bb3333408612ed9d.jpg"));

        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "redMeshPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                for (int x = 0; x < bitmap.getWidth(); x+=20) {
                    for (int y = 0; y < bitmap.getHeight(); y+=20) {
                        bitmap.setPixel(x, y, Color.WHITE);
                    }
                }
            }
        };

        // 加载渐进式图片
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse("https://drscdn.500px.org/photo/74126061/m=900_k=1_a=1/e029416848ab3b93d65c12d0a8864d6e"))
                        .setProgressiveRenderingEnabled(true)
                        // 后处理器
                        .setPostprocessor(redMeshPostprocessor)
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setAutoPlayAnimations(true)
                .build();
        pregressiveView.setController(draweeController);
    }

    @Override
    public void addListeners() {
        customTitleBar.mIvRightOperate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_right_operate:
                Intent intent = new Intent(FrescoActivity.this, NetworkImageListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
