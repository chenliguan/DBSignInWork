package com.guan.dbsignin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.guan.dbsignin.R;
import com.guan.dbsignin.db.DBOperation;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 点击添加按钮跳转到第二个activity，输入的数据要存入到数据库里面 ，
 * 第一个activity显示的内容是第二个activity输入的内容，数据从数据库里面提取。
 * 我每次打开activity1界面的时候如果数据库有数据 都要显示出来
 */
public class MainActivity extends Activity {


    @Bind(R.id.add)
    Button add;
    @Bind(R.id.lv_text)
    ListView lvText;

    private Cursor mCursor;
    /**
     * 存储数据的数组列表
     */
    private ArrayList<HashMap<String, Object>> mListData;
    /**
     * 适配器
     */
    private SimpleAdapter mListItemAdapter;
    public static DBOperation mDBOperation;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initVariable();
        getData();
        bindData();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        mDBOperation = new DBOperation(MainActivity.this);
        mListData = new ArrayList<HashMap<String, Object>>();

        // 创建数据库
        mDBOperation.openOrCreateDatabase();
    }

    /**
     * 查询所有数据,并添加到列表中
     */
    public void getData() {
        // 获取数据源，查找所有用户
        Cursor cursor = mDBOperation.selectAll();
        if (cursor != null) {
            int columnsSize = cursor.getColumnCount();
            // 获取表的内容
            while (cursor.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnsSize; i++) {
                    map.put(mDBOperation.COLUMN_ID, cursor.getString(0));
                    map.put(mDBOperation.COLUMN_USERNAME, cursor.getString(1));
                    map.put(mDBOperation.COLUMN_USERADDRES, cursor.getString(2));
                }
                mListData.add(map);
            }
            cursor.close();
        }
    }

    /**
     * 绑定数据
     */
    private void bindData() {

        mCursor = mDBOperation.selectAll();

        mListItemAdapter = new SimpleAdapter(MainActivity.this,
                // 数据源
                mListData,
                // ListItem的XML实现
                R.layout.adapter_line,
                // 动态数组与ImageItem对应的子项
                new String[]{mDBOperation.COLUMN_USERNAME, mDBOperation.COLUMN_USERADDRES},
                // XML文件里面的两个ID
                new int[]{R.id.tv_1, R.id.tv_2});
        lvText.setAdapter(mListItemAdapter);
        lvText.setOnCreateContextMenuListener(listviewLongPress);
    }

    /**
     * 触发监听
     */
    @OnClick(R.id.add)
    public void submit() {
        Intent _intent = new Intent(MainActivity.this, SubmitActivity.class);
        startActivity(_intent);
        this.finish();
    }

    /**
     * 长按事件响应
     */
    View.OnCreateContextMenuListener listviewLongPress = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {

            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("删除当前数据")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("确定删除当前记录")
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    // 获取位置索引
                                    int mListPos = info.position;
                                    // 获取对应HashMap数据内容
                                    HashMap<String, Object> map = mListData
                                            .get(mListPos);
                                    // 获取id
                                    int id = Integer.valueOf((map.get(mDBOperation.COLUMN_ID)
                                            .toString()));
                                    // 获取数组具体值后,可以对数据进行相关的操作
                                    if (mDBOperation.deleteUser(id) != -1) {
                                        // 移除listData的数据
                                        mListData.remove(mListPos);
                                        mListItemAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                }
                            }).show();
        }
    };

//    public class MainActivity extends ListActivity

//    /**
//     * 获取数据源
//     */
//    private void displayData() {
//
//        // 获取数据源，查找所有用户
//        Cursor cursor = mDBOperation.selectAll();
//        if (cursor != null) {
//            // 绑定数据源与adapter
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
//                    new String[]{mDBOperation.COLUMN_USERNAME, mDBOperation.COLUMN_USERADDRES}, new int[]{android.R.id.text1, android.R.id.text2});
//
//            // 绑定adapter到界面
//            setListAdapter(adapter);
//        }
//    }


}
