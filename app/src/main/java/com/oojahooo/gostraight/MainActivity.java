package com.oojahooo.gostraight;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int MAP_FRAGMENT = 1;
    private final int LIST_FRAGMENT = 2;

    private Button bt_map, bt_list;

    public static SQLiteDatabase mDb;
    GostraightDBHelper mDbHelper;

    private static final String DATABASE_NAME = "gostraight.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDB(this);
        this.mDbHelper = new GostraightDBHelper(this);
        this.mDb = mDbHelper.getReadableDatabase();

        bt_map = (Button)findViewById(R.id.bt_map);
        bt_list = (Button)findViewById(R.id.bt_list);

        bt_map.setOnClickListener(this);
        bt_list.setOnClickListener(this);

        callFragment(MAP_FRAGMENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_map :
                // '지도' 버튼 클릭 시 '맵 프래그먼트' 호출
                callFragment(MAP_FRAGMENT);
                break;

            case R.id.bt_list :
                // '목록' 버튼 클릭 시 '리스트 프래그먼트' 호출
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
        File databasesfolder = ctx.getDatabasePath(DATABASE_NAME).getParentFile();
        if(databasesfolder.exists()) {
        } else {
            databasesfolder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        File outfile = new File(ctx.getDatabasePath(DATABASE_NAME).getParentFile().toString() + "/" +DATABASE_NAME);
        InputStream is = null;
        FileOutputStream fos = null;
        long fileSize = 0;
        try {
            is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
            fileSize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) fileSize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fos = new FileOutputStream(outfile);
                fos.write(tempdata);
                fos.close();
            } else {}
        } catch (IOException e) {}
    }
}