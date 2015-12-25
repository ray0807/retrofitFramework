package com.bm.projectxxx.views;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 根据图片宽高比例自适应大小的ImageView
 * 
 * @author Eric
 * @since 2014-10-24
 * 
 */
public class CustomSelfSizeImageView extends ImageView {

	public CustomSelfSizeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CustomSelfSizeImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomSelfSizeImageView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec); 
		Drawable drawable = getDrawable();
		if (drawable != null) {
			int diw = drawable.getIntrinsicWidth(); // 图片资源宽度
			int dih = drawable.getIntrinsicHeight(); // 图片资源高度
			if (diw > 0 && dih > 0) {
				// 根据图片宽高自适应
				if (diw >= dih) {
					int h = width * dih / diw;
					setMeasuredDimension(width, h);
				} else {
					int w = height * diw / dih;
					setMeasuredDimension(w, height);
				}
			} else {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
		}
		
	}

}
