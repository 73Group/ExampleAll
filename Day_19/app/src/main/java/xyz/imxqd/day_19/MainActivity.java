package xyz.imxqd.day_19;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * HandlerThread的简单使用示例
 */
public class MainActivity extends AppCompatActivity {

    private static String TAG = "HandlerThread";
    private Handler handler;
    private HandlerThread thread = new HandlerThread("MyHandlerThread");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // HandlerThread在start之后会一直处于待命状态
        thread.start();
        // HandlerThread使用HandlerThread的Looper去创建一个Handler
        // 该Handler使用post方法执行Runnable接口将会在该HandlerThread种执行
        // 同样地，该Handler的handleMessage方法也在该线程中
        // 你可以使用Handler(Looper looper, Callback callback)构造方法
        // 来实现sendMessage和handleMessage，但是在这个handleMessage方法中不能对UI进行更新操作
        // 所以不能简单地将Handler理解为更新UI的工具
        // Handler在那个线程执行取决于它的Looper所在的线程
        // 你可以通过Looper.getMainLooper()获取主线程的Looper
        handler = new Handler(thread.getLooper());

        // 可以使用Handler的post方法将Runnable对象放入该线程的执行队列中去
        // Runnable对象将会被逐个按顺序执行
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate");
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "1");
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "2");
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "3");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onStart");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onStop");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onDestroy");
                // HandlerThread 在使用完毕之后一定要quit，否则它会一直占用资源
                thread.quit();
            }
        });

        // 在这里调用quit()有可能导致有一些队列中Runnable对象没有执行完毕
        // 因为quit()主线程中调用的
        // thread.quit();


        // 在调用了thread.quit()之后，post的Runnable将不能被执行
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "after quit");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onPause");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onResume");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onRestart");
            }
        });
    }
}
