package xyz.imxqd.day_21;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
}
