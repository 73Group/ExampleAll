package xyz.imxqd.day_15.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyz.imxqd.day_15.utils.Constants;

/**
 * Created by imxqd on 2016/12/10.
 */

public class NoteDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "note";

    public NoteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ Constants.DB_TABLE_NOTE +
                "(" + Constants.DB_COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.DB_COL_TIME + " LONG, " +
                Constants.DB_COL_CONTENT + " text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
