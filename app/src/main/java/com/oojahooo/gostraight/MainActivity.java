package com.oojahooo.gostraight;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int MAP_FRAGMENT = 1;
    private final int LIST_FRAGMENT = 2;

    private Button bt_tab1, bt_tab2;

    private DBAdapter mDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBAdapter = new DBAdapter(this);
        mDBAdapter.open();

        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2);

        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);

        callFragment(MAP_FRAGMENT);
    }

    @Override
    protected void onDestroy() {
        mDBAdapter.close();
        super.onDestroy();
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

    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
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
}