package com.fengyu.fcapture.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fengyu.fcapture.app.FApplication;
import com.fengyu.fcapture.record.RecordHelper;


/**
 */
public class RecordService extends Service {
    private final static String EXTRA_RESULT_CODE = "extra_result_code";
    private final static String EXTRA_DATA = "extra_data";

    RecordHelper.Listener listener = new RecordHelper.Listener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {
            stopSelf();
        }

    };
    private boolean running = false;
    private RecordHelper recordHelper;

    public static Intent newIntent(Context context, int resultCode, Intent data) {
        Intent intent = new Intent(context, RecordService.class);
        intent.putExtra(EXTRA_RESULT_CODE, resultCode);
        intent.putExtra(EXTRA_DATA, data);
        return intent;
    }

    @Override
    public void onDestroy() {
        recordHelper.onDestory();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (running) {
            return START_NOT_STICKY;
        }
        running = true;
        int resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0);
        Intent data = intent.getParcelableExtra(EXTRA_DATA);
        if (resultCode == 0 || data == null) {
            throw new IllegalStateException("Result code or data missing.");
        }
        recordHelper = new RecordHelper(FApplication.getApplication());
        recordHelper.initial(resultCode, data, listener);
        recordHelper.showOverlayView();
        return START_NOT_STICKY;
    }

}
