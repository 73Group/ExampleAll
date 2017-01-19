package xyz.imxqd.day_18;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    // 以这种方式直接创建的Handler运行在当前线程中，Activity的线程是主线程，所以它可以对UI进行操作
    // 注意不要太死板，你也可以直接通过自定义类继承Handler，覆盖重写handleMessage方法
    // 类似于下面这样
    //    class MyHandler extends Handler {
    //        @Override
    //        public void handleMessage(Message msg) {
    //        }
    //    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            textView.setText(data.getString("text"));
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    if (data.getBoolean("res")) {
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);

//        实际上这段代码并不会导致程序异常，具体原因查看Android源码可知，
//        Android是在视图第一次创建并显示之后进行的UI更新操作才会检查线程
//        实际上你不需要过多关注这个，只需要记住你只能在主线程更新UI
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                textView.setText("start");
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 最好使用obtainMessage方法来获取Message以节省内存使用
                    Message msg = handler.obtainMessage(0);
                    Bundle data = new Bundle();
                    // 可以将一些数据放入data中
                    data.putString("text", "开始");
                    msg.setData(data);
                    handler.sendMessage(msg);
                    Thread.sleep(3000);
                    // 在这里用sleep模拟一些耗时操作
                    msg = handler.obtainMessage(1);
                    data.putString("text", "停止");
                    data.putBoolean("res", true);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        这段代码在程序切换到后台再切换回来的时候导致程序异常
//        实际上你不需要过多关注这个，只需要记住你只能在主线程更新UI
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                textView.setText("start");
//            }
//        }).start();
    }
}
