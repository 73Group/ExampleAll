package xyz.imxqd.day_14;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by imxqd on 2016/12/6.
 * SQLiteOpenHelper是Android提供的数据库创建和版本管理的辅助类，使用数据库时推荐使用这种方式
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {

    // 当前数据库的版本号
    private static final int VERSION = 1;
    // 数据库名称
    private static final String NAME = "test";

    public MyDBOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }


    // 此方法会在数据库不存在时，第一次调用getWritableDatabase()时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 这里可以执行多条SQL语句
        db.execSQL("CREATE TABLE user (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_name text UNIQUE, " +
                "password text NOT NULL);");
        // 注意：这里写完后不要调用db.close()方法，否则getWritableDatabase()
        // 获取到的数据库将不可用
    }

    // 由于应用程序的升级，数据库可能也会跟随升级。通过SQLiteOpenHelper管理数据库时，
    // 如果VERSION大于当前数据库版本，则会执行此方法，用于数据表的创建，修改和迁移
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
