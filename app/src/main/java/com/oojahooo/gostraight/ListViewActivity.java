package com.oojahooo.gostraight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    List<String> listview_iprints;
    ArrayAdapter<String> listview_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listview = (ListView)findViewById(R.id.iprint_listview);
        listview_iprints = new ArrayList<>();
        listview_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listview_iprints);
        listview.setAdapter(listview_adapter);
    }
}
