package com.oojahooo.gostraight;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int MAP_FRAGMENT = 1;
    private final int LIST_FRAGMENT = 2;

    private Button bt_tab1, bt_tab2;

    public static final String ROOT_DIR = "/data/data/com.oojahooo.gostraight/databases/";

    public SQLiteDatabase mDb;
    public Cursor cursor;
    GostraightDBHelper mDbHelper;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS FACILITY (_id integer primary key autoincrement, category integer, section integer, detail text not null);";
    private static final String DATABASE_NAME = "gostraight.db";
    private static final String DATABASE_TABLE = "FACILITY";
    private static final int DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDB(this);
        mDbHelper = new GostraightDBHelper(this);
        mDb = mDbHelper.getReadableDatabase();

        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2);

        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);

        callFragment(MAP_FRAGMENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab1 :
                // '버튼1' 클릭 시 '맵 프래그먼트' 호출
                callFragment(MAP_FRAGMENT);
                break;

            case R.id.bt_tab2 :
                // '버튼2' 클릭 시 '리스트 프래그먼트' 호출
                callFragment(LIST_FRAGMENT);
                break;
        }
    }

    private void callFragment(int fragment_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment_no){
            case 1:
                // '맵 프래그먼트' 호출
                MapFragment mf = new MapFragment();
                transaction.replace(R.id.fragment_container, mf);
                transaction.commit();
                break;

            case 2:
                // '리스트 프래그먼트' 호출
                ListIprintFragment lif = new ListIprintFragment();
                transaction.replace(R.id.fragment_container, lif);
                transaction.commit();
                break;
        }

    }

    public static void setDB(Context ctx) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        File outfile = new File(ROOT_DIR+DATABASE_NAME);
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {}
        } catch (IOException e) {}
    }
}