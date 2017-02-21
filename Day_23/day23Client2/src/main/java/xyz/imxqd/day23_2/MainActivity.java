package xyz.imxqd.day23_2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import day_23.IMyService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = "Client2";

    private boolean isConnected = false;
    private IMyService remoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        if (isConnected) {
            unbindService(this);
            isConnected = false;
        }
        super.onDestroy();
    }

    public void serviceBind(View view) {
        Log.d(TAG, "serviceBind: ");
        Intent intent = new Intent();
        intent.setAction("xyz.imxqd.day_23.MyService.action");
        intent.setPackage("xyz.imxqd.day_23");
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    public void serviceUnbind(View view) {
        Log.d(TAG, "serviceUnbind: ");
        if (isConnected) {
            unbindService(this);
            isConnected = false;
        }
    }

    public void binderStart(View view) {
        Log.d(TAG, "binderStart: ");
        if (isConnected && remoteService != null) {
            try {
                remoteService.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void binderPause(View view) {
        Log.d(TAG, "binderPause: ");
        if (isConnected && remoteService != null) {
            try {
                remoteService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void binderCurrent(View view) {
        Log.d(TAG, "binderCurrent: ");
        if (isConnected && remoteService != null) {
            try {
                System.out.println(remoteService.getCurrent());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected: ");
        isConnected = true;
        remoteService = IMyService.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onServiceDisconnected: ");
        isConnected = false;
    }

    public void serviceStart(View view) {
        Log.d(TAG, "serviceStart: ");
        Intent intent = new Intent();
        intent.setAction("xyz.imxqd.day_23.MyService.action");
        intent.setPackage("xyz.imxqd.day_23");
        startService(intent);
    }

    public void serviceStop(View view) {
        Log.d(TAG, "serviceStop: ");
        Intent intent = new Intent();
        intent.setAction("xyz.imxqd.day_23.MyService.action");
        intent.setPackage("xyz.imxqd.day_23");
        stopService(intent);
    }
}
