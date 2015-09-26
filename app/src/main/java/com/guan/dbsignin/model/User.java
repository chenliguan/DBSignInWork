package com.guan.dbsignin.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.guan.dbsignin.R;

/**
 * 用户对象类
 *
 * @author Guan
 * @file com.guan.store.sqlite
 * @date 2015/8/31
 * @Version 1.0
 */
public class User {

    private String userName;
    private String userAddress;

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}



