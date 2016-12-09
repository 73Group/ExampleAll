package com.example.cx.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends Activity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        rv = new RecyclerView(this);
        //把rv作为Activity的内容布局
        setContentView(rv);

        //设置布局，使用表格布局
        rv.setLayoutManager(new GridLayoutManager(this,3));

        rv.setAdapter(new MyAdapter());
    }

}
