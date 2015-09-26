package com.guan.dbsignin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBOpenHelper创建数据库中的表,并做适当的初始化工作
 *
 * @author Guan
 * @file com.guan.store.sqlite
 * @date 2015/9/1
 * @Version 1.0
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private final String DATABASE_NAME = "androidDBStorageSqlite";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_USERADDRES = "userAddress";

    public static final String DATABASE_CREATE = "Create TABLE  if not exists MAIN.[" + TABLE_NAME + "](\n" +
            "[_id] integer PRIMARY KEY NOT NULL\n" +
            ",[" + COLUMN_USERNAME + "] integer NOT NULL\n" +
            ",[" + COLUMN_USERADDRES + "] integer NOT NULL\n" +
            "   \n" +
            ");";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion == 2) {
            db.execSQL(DATABASE_CREATE);
        } else if (oldVersion == 2 && newVersion == 1) {
            db.execSQL("drop table MAIN.[" + TABLE_NAME + "]");
        }
    }


}
