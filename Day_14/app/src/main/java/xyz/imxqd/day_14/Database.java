package xyz.imxqd.day_14;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by imxqd on 2016/12/6.
 * 此类封装了对数据库的一系列操作，推荐使用这种方式把数据库操作封装而不是直接进行SQL操作
 */

public class Database {
    private static volatile Database db;
    private MyDBOpenHelper helper;

    /**
     * 这里抛出RuntimeException，防止有人通过反射来获取Database对象（自己一个人写的软件其实没必要这样写）
     */
    private Database() {
        throw new RuntimeException();
    }
    private Database(Context context) {
        helper = new MyDBOpenHelper(context);
    }

    /**
     * 采用单例模式
     * @param context 上下文对象
     * @return 全局唯一的Database
     */
    public static synchronized Database getInstance(Context context) {
        if (db == null) {
            db = new Database(context);
        }
        return db;
    }

    public void insertUser(String userName, String password) {
        // ContentValues是一个键值对，用于描述SQLite的一条记录
        ContentValues v = new ContentValues(2);
        v.put("user_name", userName);
        v.put("password", password);
        SQLiteDatabase database = helper.getWritableDatabase();
        database.insert("user", null, v);
        database.close();
    }

    public void deleteUser(String userName) {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("user", "user_name = ?", new String[] {userName});
        // 也可以这么写
        // database.execSQL("delete from user where user_name = " + userName);
        // 或者这么写
        // database.execSQL("delete from user where user_name = ?", new String[]{userName});
        database.close();
    }

    public void updatePwd(String userName, String pwd) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", pwd);
        database.update("user", values, "where user_name = ?" , new String[] {userName});
        // 也可以这么写
        // database.execSQL("update user set password = ? where user_name = ?", new String[]{pwd, userName});
        // 或者这么写
        // database.execSQL(String.format("update user set password = %s where user_name = %s", pwd, userName));
        database.close();
    }

    public ArrayList<String> getAllUserName() {
        ArrayList<String> userNameList = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
        while (cursor.moveToNext()) {
            userNameList.add(cursor.getString(1));
        }
        cursor.close();
        database.close();
        return userNameList;
    }
}
