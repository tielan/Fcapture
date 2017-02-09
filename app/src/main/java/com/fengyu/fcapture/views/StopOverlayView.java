package com.fengyu.fcapture.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fengyu.fcapture.R;

import static android.graphics.PixelFormat.TRANSLUCENT;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;

/**
 * Created by jabin on 7/12/15.
 */
public class StopOverlayView extends FrameLayout implements View.OnClickListener {

    private StopListener listener;
    private TextView tvStop;
    private int animationWidth;
    private Handler handler = new Handler();
    private String stopTitle;
    private int TIME = 1000;
    int i = 0;

    public StopOverlayView(Context context, StopListener listener) {
        super(context);
        this.listener = listener;
        inflate(context, R.layout.stop_view, this);
        int width = context.getResources().getDimensionPixelOffset(R.dimen.overlay_width);
        animationWidth = width;
        tvStop = (TextView) findViewById(R.id.Stop);
        tvStop.setOnClickListener(this);
        tvStop.setWidth(width);
        stopTitle = context.getResources().getString(R.string.stop);
        tvStop.setText(stopTitle + "   "+formatDateTime(i++));
        handler.postDelayed(runnable, TIME); //每隔1s执行
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 1000);
                tvStop.setText(stopTitle + "   "+formatDateTime(i++));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static String formatDateTime(int mss) {
        String DateTimes = "";
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (hours > 0) {
            DateTimes = hours + " " + minutes + ":"+ seconds;
        } else if (minutes > 0) {
            if(minutes < 10){
                DateTimes += "0";
            }
            DateTimes += minutes + "：" + seconds + "";
        } else {
            DateTimes = "00:";
            if(seconds < 10){
                DateTimes += "0";
            }
            DateTimes +=seconds+"";
        }

        return DateTimes;
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        LayoutInflater factory = LayoutInflater.from(context);
        return factory.inflate(resource, root);
    }

    public static StopOverlayView create(Context context, StopListener listener) {
        return new StopOverlayView(context, listener);
    }


    public static WindowManager.LayoutParams createLayoutParams(Context context) {
        Resources res = context.getResources();
        int width = res.getDimensionPixelSize(R.dimen.overlay_width);
        int height = res.getDimensionPixelSize(R.dimen.overlay_height);
        if (Build.VERSION.SDK_INT > LOLLIPOP_MR1 || "M".equals(Build.VERSION.RELEASE)) {
            height = res.getDimensionPixelSize(R.dimen.overlay_height_m);
        }
        final WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(width, height, TYPE_SYSTEM_ERROR, FLAG_NOT_FOCUSABLE
                        | FLAG_NOT_TOUCH_MODAL
                        | FLAG_LAYOUT_NO_LIMITS
                        | FLAG_LAYOUT_INSET_DECOR
                        | FLAG_LAYOUT_IN_SCREEN, TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

        return params;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTranslationX(animationWidth);
        animate().translationX(0)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator());
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Stop) {
            listener.stop();
        }
    }

    public interface StopListener {
        void stop();
    }
}
