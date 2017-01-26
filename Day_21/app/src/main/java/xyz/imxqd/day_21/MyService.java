package xyz.imxqd.day_21;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public static final String ACTION_VISIT = "visit";
    public static final String ARG_URL = "url";

    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (ACTION_VISIT.equals(intent.getAction())) {
            String url = intent.getStringExtra(ARG_URL);
            Log.d(TAG, "我访问了" + url);
        } else {
            Log.d(TAG, "不支持的操作");
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }
    
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }
}
