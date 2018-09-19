package io.ztc.indexlistview;

import android.view.View;

public abstract class IndexNoDoubleListener implements View.OnClickListener {
    private int mThrottleFirstTime = 1000;
    private long mLastClickTime = 0;

    public IndexNoDoubleListener() {
    }

    public IndexNoDoubleListener(int throttleFirstTime) {
        mThrottleFirstTime = throttleFirstTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
