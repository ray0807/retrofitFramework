package com.bm.projectxxx.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bm.projectxxx.R;

/**
 * 自定义尺寸比例ImageView
 * 
 */
@SuppressLint("NewApi")
public class CustomSelfProportionImageView extends ImageView {
	private int w = 1; // 宽比例
	private int h = 1; // 高比例

	public CustomSelfProportionImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomSelfProportionImageView);
		this.h = typedArray.getInt(R.styleable.CustomSelfProportionImageView_h, 1);
		this.w = typedArray.getInt(R.styleable.CustomSelfProportionImageView_w, 1);
		typedArray.recycle();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		if (width > 0 && w > 0 && h > 0) {
			int newH = (int) (width * h / w);
			setMeasuredDimension(width, newH);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}

}
