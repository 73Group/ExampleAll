package xyz.imxqd.day_15.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xyz.imxqd.day_15.dao.model.Note;
import xyz.imxqd.day_15.utils.Constants;

/**
 * Created by imxqd on 2016/12/10.
 * 数据库操作的封装类
 */

public class NoteDB {
    private static volatile NoteDB db;
    private NoteDBHelper helper;

    private NoteDB(Context context) {
        helper = new NoteDBHelper(context);
    }

    /**
     * 采用单例模式
     * @param context 上下文对象
     * @return 全局唯一的Database
     */
    public static synchronized NoteDB getInstance(Context context) {
        if (db == null) {
            db = new NoteDB(context);
        }
        return db;
    }

    public void addNote(Note note) {
        SQLiteDatabase sdb = helper.getWritableDatabase();
        ContentValues values = new ContentValues(2);
        values.put(Constants.DB_COL_TIME, note.getTime());
        values.put(Constants.DB_COL_CONTENT, note.getContent());
        sdb.insert(Constants.DB_TABLE_NOTE, null, values);
        sdb.close();
    }

    public void delNote(int id) {
        SQLiteDatabase sdb = helper.getWritableDatabase();
        sdb.delete(Constants.DB_TABLE_NOTE, "id = ?", new String[] {String.valueOf(id)});
        sdb.close();
    }

    public void updateNote(Note note) {
        SQLiteDatabase sdb = helper.getWritableDatabase();
        ContentValues values = new ContentValues(3);
        values.put(Constants.DB_COL_ID, note.getId());
        values.put(Constants.DB_COL_TIME, note.getTime());
        values.put(Constants.DB_COL_CONTENT, note.getContent());
        sdb.update(Constants.DB_TABLE_NOTE, values, "id = ?", new String[] {String.valueOf(note.getId())});
        sdb.close();
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> list = new ArrayList<>();
        SQLiteDatabase sdb = helper.getReadableDatabase();
        Cursor cursor =
        sdb.rawQuery("SELECT * FROM " + Constants.DB_TABLE_NOTE + " ORDER BY time DESC", null);
        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setId(cursor.getInt(Constants.DB_COL_ID_INDEX));
            note.setTime(cursor.getLong(Constants.DB_COL_TIME_INDEX));
            note.setContent(cursor.getString(Constants.DB_COL_CONTENT_INDEX));
            list.add(note);
        }
        cursor.close();
        sdb.close();
        return list;
    }

}
