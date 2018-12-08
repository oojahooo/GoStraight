package com.oojahooo.gostraight;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int MAP_FRAGMENT = 1;
    private final int LIST_FRAGMENT = 2;

    private Button bt_map, bt_list;

    public static SQLiteDatabase mDb;
    GostraightDBHelper mDbHelper;
    Cursor cursor;

    private static final String DATABASE_NAME = "gostraight.db";

    public static final int IPRINT = 0;
    public static final int WATER = 1;
    public static final int VENDING = 2;
    public static final int ATM = 3;

    public static List<String> listview_iprints;
    int category, building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDB(this);
        this.mDbHelper = new GostraightDBHelper(this);
        this.mDb = mDbHelper.getReadableDatabase();
        this.listview_iprints = new ArrayList<>();

        cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

        String iprinttext;

        if((cursor != null && cursor.getCount() != 0)) {
            cursor.moveToFirst();
            do {
                iprinttext = "";
                int category = cursor.getInt(1);
                switch (category) {
                    case IPRINT:
                        iprinttext += "아이프린트, ";
                        break;
                    case WATER:
                        iprinttext += "정수기, ";
                        break;
                    case VENDING:
                        iprinttext += "자판기, ";
                        break;
                    case ATM:
                        iprinttext += "ATM, ";
                        break;
                }
                iprinttext += cursor.getString(2) + ", " + cursor.getString(3);
                this.listview_iprints.add(iprinttext);
                cursor.moveToNext();
            } while(!cursor.isLast());
        }

        bt_map = (Button)findViewById(R.id.bt_map);
        bt_list = (Button)findViewById(R.id.bt_list);

        bt_map.setOnClickListener(this);
        bt_list.setOnClickListener(this);

        final Spinner spinner_categories = (Spinner)findViewById(R.id.category_spinner);
        spinner_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner spinner_buildings = (Spinner)findViewById(R.id.building_spinner);
        spinner_buildings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                building = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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