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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAP_FRAGMENT = 1;
    public static final int LIST_FRAGMENT = 2;
    public static int FRAGMENT_STATE = MAP_FRAGMENT;

    private Button bt_map, bt_list;

    public static SQLiteDatabase mDb;
    GostraightDBHelper mDbHelper;
    Cursor cursor;

    private static final String DATABASE_NAME = "gostraight.db";

    public static final int IPRINT = 1;
    public static final int WATER = 2;
    public static final int VENDING = 3;
    public static final int ATM = 4;

    public static List<String> listview_iprints;
    public static List<String> dialog_iprints;
    public static int category = 0;
    public static int section = 0;
    public static HashMap<Integer, ArrayList> sectionBuilding = new HashMap<Integer, ArrayList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < 10; i++) {
            sectionBuilding.put(i, new ArrayList<String>());
        }
        sectionBuilding.put(1, new ArrayList<String>(Arrays.asList("하나스퀘어", "과학도서관", "아산이학관", "산학관", "하나과학관", "생명과학관(서관)", "CJ식품안전관")));
        sectionBuilding.put(2, new ArrayList<String>(Arrays.asList("생명과학관(동관)", "우정정보관", "미래융합기술관", "애기능생활관")));
        sectionBuilding.put(3, new ArrayList<String>(Arrays.asList("공학관", "이학관별관", "창의관", "신공학관", "애기능학생회관")));
        sectionBuilding.put(4, new ArrayList<String>(Arrays.asList("정경관", "미디어관", "우당교양관", "타이거프라자", "국제관", "인촌기념관")));
        sectionBuilding.put(5, new ArrayList<String>(Arrays.asList("우당교양관", "학생회관", "홍보관", "4.18기념관", "SK미래관")));
        sectionBuilding.put(6, new ArrayList<String>(Arrays.asList("SK미래관", "서관(문과대학)", "본관", "중앙광장지하", "본관", "100주년기념삼성관")));
        sectionBuilding.put(7, new ArrayList<String>(Arrays.asList("대학원도서관", "법학관구관", "법학관신관", "해송법학도서관", "동원글로벌리더쉽홀", "CJ법학관", "아세아문제연구소", "법학관신관")));
        sectionBuilding.put(8, new ArrayList<String>(Arrays.asList("사범대학본관", "사범대학신관", "운초우선교육관", "체육생활관", "교우회관", "현대자동차경영관", "경영본관", "LG-POSCO경영관")));

        final Spinner spinner_categories = (Spinner)findViewById(R.id.category_spinner);
        spinner_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = position;
                MapFragment.mapPosition = -1;
                callFragment(FRAGMENT_STATE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        final Spinner spinner_buildings = (Spinner)findViewById(R.id.building_spinner);
        spinner_buildings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                section = position;
                MapFragment.mapPosition = -1;
                callFragment(FRAGMENT_STATE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        setDB(this);
        this.mDbHelper = new GostraightDBHelper(this);
        this.mDb = mDbHelper.getReadableDatabase();
        this.listview_iprints = new ArrayList<>();
        this.dialog_iprints = new ArrayList<>();

        cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

        String iprinttext;

        if((cursor != null && cursor.getCount() != 0)) {
            cursor.moveToFirst();
            do {
                iprinttext = "";
                int newcategory = cursor.getInt(1);

                if(category == 0 || newcategory == category) {
                    switch (newcategory) {
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
                    iprinttext += cursor.getString(2);
                    this.listview_iprints.add(iprinttext);
                    this.dialog_iprints.add(cursor.getString(3));
                }
                cursor.moveToNext();
            } while(!cursor.isLast());
        }

        bt_map = (Button)findViewById(R.id.bt_map);
        bt_list = (Button)findViewById(R.id.bt_list);

        bt_map.setOnClickListener(this);
        bt_list.setOnClickListener(this);

        callFragment(MAP_FRAGMENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_map :  // '지도' 버튼 클릭 시 '맵 프래그먼트' 호출
                FRAGMENT_STATE = MAP_FRAGMENT;
                callFragment(MAP_FRAGMENT);
                break;

            case R.id.bt_list :  // '목록' 버튼 클릭 시 '리스트 프래그먼트' 호출
                FRAGMENT_STATE = LIST_FRAGMENT;
                callFragment(LIST_FRAGMENT);
                break;
        }
    }

    public void callFragment(int fragment_no){

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