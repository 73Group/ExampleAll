package xyz.imxqd.day_23;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import day_23.IMyService;

public class MyService extends Service {
    public static final String ACTION_VISIT = "visit";
    public static final String ARG_URL = "url";

    private static final String TAG = "MyService";

    private int count = 0;
    private boolean isPaused = false;

    private IMyService.Stub binder = new IMyService.Stub() {
        @Override
        public void start() throws RemoteException {
            Log.d(TAG, "start: ");
            isPaused = false;
        }

        @Override
        public void pause() throws RemoteException {
            Log.d(TAG, "pause: ");
            isPaused = true;
        }


        @Override
        public int getCurrent() throws RemoteException {
            Log.d(TAG, "getCurrent: ");
            return count;
        }
    };
    private Timer timer;

    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        timer = new Timer(true);

        // 延迟一秒执行，周期一秒
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isPaused) {
                        count++;
                    }
                }
            }, 1000, 1000);
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
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        timer.cancel();
        super.onDestroy();
    }
}
