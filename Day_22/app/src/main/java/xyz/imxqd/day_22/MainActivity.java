package xyz.imxqd.day_22;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// 注意：这里MainActivity实现了ServiceConnection接口
public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private boolean isConnected = false;
    private MyService.MyServiceBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void serviceVisit(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.setAction(MyService.ACTION_VISIT);
        intent.putExtra(MyService.ARG_URL, "http://www.baidu.com/");
        startService(intent);
    }

    public void serviceGo(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.setAction("go");
        startService(intent);
    }

    public void serviceBind(View view) {
        Intent intent = new Intent(this, MyService.class);
        // MainActivity实现了ServiceConnection接口才能这么写
        // 第三个参数是FLAG，一般填写BIND_AUTO_CREATE即可
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    public void serviceUnBind(View view) {
        if (isConnected) {
            unbindService(this);
            isConnected = false;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        isConnected = true;
        binder = (MyService.MyServiceBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isConnected = false;
    }

    public void binderStart(View view) {
        if (isConnected && binder != null) {
            binder.start();
        }
    }

    public void binderPause(View view) {
        if (isConnected && binder != null) {
            binder.pause();
        }
    }



    public void binderCurrent(View view) {
        if (isConnected && binder != null) {
            System.out.println(binder.getCurrent());
        }
    }

    public void serviceStop(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
