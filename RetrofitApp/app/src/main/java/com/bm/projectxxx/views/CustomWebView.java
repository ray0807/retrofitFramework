package com.bm.projectxxx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 自定义带进度条的 webview
 * 
 * @author 赵成龙
 * 
 */
public class CustomWebView extends WebView {
	private ProgressBar progressBar;

	@SuppressWarnings("deprecation")
	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
		addView(progressBar);
		setWebChromeClient(new WebChromeClient());
	}

	private class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressBar.setVisibility(GONE);
			} else {
				if (progressBar.getVisibility() == GONE)
					progressBar.setVisibility(VISIBLE);
				progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressBar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(false);
		return super.dispatchTouchEvent(ev);
	}

}
