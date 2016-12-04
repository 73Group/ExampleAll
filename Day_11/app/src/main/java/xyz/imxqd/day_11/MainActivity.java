package xyz.imxqd.day_11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子是演示OptionsMenu菜单和RecyclerView事件绑定的例子
 */
public class MainActivity extends Activity implements MyAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("陈晓", "13396341578", true, User.Sex.Female));
        list.add(new User("陈晓", "18225600952", false, User.Sex.Female));
        list.add(new User("徐啟东", "15665515162", true, User.Sex.Male));
        list.add(new User("徐啟东", "15665515162", false, User.Sex.Male));
        list.add(new User("陈晓", "13396341578", false, User.Sex.Female));
        list.add(new User("陈晓", "18225600952", false, User.Sex.Female));
        list.add(new User("徐啟东", "15665515162", false, User.Sex.Male));
        list.add(new User("徐啟东", "15665515162", false, User.Sex.Male));
        list.add(new User("陈晓", "13396341578", false, User.Sex.Female));
        list.add(new User("陈晓", "18225600952", false, User.Sex.Female));
        list.add(new User("徐啟东", "15665515162", false, User.Sex.Male));
        adapter = new MyAdapter(list);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                adapter.add(new User("XXX", "12345678910", false, User.Sex.Male));
                // 数据集发生改变，通知RecyclerView重新绘制
                adapter.notifyDataSetChanged();
                Toast.makeText(this, R.string.action_add, Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_clear:
                adapter.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, R.string.action_clear, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                final List<User> list = adapter.getCheckedUsers();
                new AlertDialog.Builder(this)
                        .setTitle("选中项")
                        .setMessage(list.toString())
                        .setPositiveButton("分享", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 隐式启动的演示
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "分享演示");
                                intent.putExtra(Intent.EXTRA_TEXT, list.toString());
                                startActivity(intent);
                            }
                        })
                        .show();

                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_example:
                startActivity(new Intent(this, WidgetActivity.class));
        }
        return true;
    }

    @Override
    public void onItemClick(User user, int pos) {
        Toast.makeText(this, "item:" + user.getName(), Toast.LENGTH_SHORT).show();
        adapter.remove(pos);
        // 这里也可以使用adapter.notifyDataSetChanged()，
        // 但这样会全部重新绘制，效率更低，而且没有动画
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void onIconClick(User user, int pos) {
        Toast.makeText(this, "icon:" + user.getName(), Toast.LENGTH_SHORT).show();
        // AlertDialog的使用，很简单
        new AlertDialog.Builder(this)
                .setTitle("详细信息")
                .setMessage(user.detailString())
                .setPositiveButton("确定", null)
                .show();
    }
}
