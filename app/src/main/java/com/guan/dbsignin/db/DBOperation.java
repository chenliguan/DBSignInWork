package com.guan.dbsignin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guan.dbsignin.model.User;

/**
 * DBOperation实现数据库的各项操作，包括：建立及打开数据库、增、删、改、查等
 *
 * @author Guan
 * @file com.guan.store.sqlite
 * @date 2015/8/31
 * @Version 1.0
 */
public class DBOperation {

    private Context mContext;
    private String firstTabName = null;
    private SQLiteDatabase mSqLiteDatabase;
    private final String DATABASE_NAME = "androidDBStorageSqlite";

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_USERADDRES = "userAddress";

    public DBOperation(Context pContext) {
        this.mContext = pContext;
    }

    /**
     * 建立及打开数据库
     */
    public void openOrCreateDatabase() {

        try {
            DBOpenHelper _helper = new DBOpenHelper(mContext,DATABASE_NAME,null,2);
            mSqLiteDatabase = _helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 插入用户
     * @param user
     */
    public long insertUser(User user) {

        if (mSqLiteDatabase != null && user != null) {
            // 插入一条记录
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, user.getUserName());
            values.put(COLUMN_USERADDRES, user.getUserAddress());
            return mSqLiteDatabase.insert(TABLE_NAME, "", values);
        }
        return -1;
    }

    /**
     * 查询所有记录
     */
    public Cursor selectAll() {

        if (mSqLiteDatabase != null) {
            return mSqLiteDatabase.query(TABLE_NAME, new String[]{"_id", COLUMN_USERNAME, COLUMN_USERADDRES}, null, null, null, null, null);
        }

        return null;
    }

    /**
     * 根据条件查询用户
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     */
    public Cursor selectUser(String selection,String[] selectionArgs,String groupBy,String having,String orderBy) {

        return mSqLiteDatabase.query(TABLE_NAME,new String[]{"_id",COLUMN_USERNAME, COLUMN_USERADDRES},selection,selectionArgs
        ,groupBy,having,orderBy);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public int deleteUser(int id) {

        if (mSqLiteDatabase != null && id > 0) {
            return mSqLiteDatabase.delete(TABLE_NAME,"_id=?",new String[]{String.valueOf(id)});
        }
        return -1;
    }

    /**
     * 更新用户
     * @param user
     * @param id
     * @return
     */
    public int updateUser(User user,int id) {

        if (mSqLiteDatabase != null && user != null) {
            ContentValues _values = new ContentValues();
            _values.put(COLUMN_USERNAME,user.getUserName());
            _values.put(COLUMN_USERADDRES,user.getUserAddress());
            return mSqLiteDatabase.update(TABLE_NAME, _values, "_id=?", new String[]{String.valueOf(id)});
        }
        return -1;
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase() {

        mSqLiteDatabase.close();
        mSqLiteDatabase = null;
    }
}




