package com.bm.projectxxx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 正方形的ImageView
 * 
 * @author 赵成龙
 * @since 2014-11-23
 * 
 */
public class CustomSquareImageView extends SimpleDraweeView {

	public CustomSquareImageView(Context context) {
		super(context);
	}

	public CustomSquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = View.MeasureSpec.getSize(widthMeasureSpec);
		// 强制设置为正方形
		setMeasuredDimension(width, width);
	}

}
