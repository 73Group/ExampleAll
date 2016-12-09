package xyz.imxqd.day_14;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

/**
 * Android数据库操作的示例代码
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 向数据库的用户表中插入两条记录，由于是user_name有唯一约束，在第二次运行此Activity时会插入失败
        Database.getInstance(this).insertUser("hfutxqd", "12312312312");
        Database.getInstance(this).insertUser("hfutcx", "12312312312");

        // 查询数据库中用户表中所有的用户名
        Log.d(TAG, "userNames : " + Database.getInstance(this).getAllUserName());

        // 这里通过另外一种方法来创建或打开SQLite数据库
        SQLiteDatabase database = openOrCreateDatabase("test_2", Context.MODE_PRIVATE, null);
        // 新建一张表
        database.execSQL("CREATE TABLE IF NOT EXISTS test (test_id INTEGER, test_xxx TEXT)");
        database.close();

        for (String dbn: databaseList()) {
            SQLiteDatabase db = openOrCreateDatabase(dbn, Context.MODE_PRIVATE, null);
            // 以-journal结尾的数据库是用于事务回滚的，不是真正的数据库
            if (dbn.endsWith("-journal")) {
                continue;
            }
            Log.d(TAG, "database name:" + dbn);
            // 获取某个数据库的所有表名
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master " +
                    "WHERE type='table' ORDER BY name", null);
            // 游标的初始位置是在-1，在第一次moveToNext()后才是第一条记录，如果没有记录了，直接返回false
            while(cursor.moveToNext()){
                String name = cursor.getString(0);
                Log.d(TAG, "table " + name + ": \n");
                Cursor c = db.rawQuery("SELECT * FROM " + name, null);
                // 获取所有字段名
                String[] tabs = c.getColumnNames();
                StringBuilder tableHeader = new StringBuilder();
                for (String t: tabs) {
                    tableHeader.append(t);
                    tableHeader.append('\t');
                }
                Log.d(TAG, "______________________________________");
                Log.d(TAG, tableHeader + "\n");
                while (c.moveToNext()) {
                    // 依次获取本条记录所有字段的值，并把它们拼接成一个字符串
                    StringBuilder content = new StringBuilder();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        content.append(c.getString(i));
                        content.append('\t');
                    }
                    content.append('\n');
                    Log.d(TAG, content.toString());
                }
                Log.d(TAG, "-------------------------------------");
                // 游标使用完毕需要关闭
                c.close();
            }
            cursor.close();
            db.close();
        }
    }
}
