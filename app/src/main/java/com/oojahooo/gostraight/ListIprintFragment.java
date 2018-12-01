package com.oojahooo.gostraight;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.database.sqlite.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListIprintFragment extends Fragment {
    List<String> listview_iprints;
    ArrayAdapter<String> listview_adapter;
    final int IPRINT = 0;
    final int WATER = 1;
    final int VENDING = 2;
    final int ATM = 3;
    int category, building;

    Cursor cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

    public ListIprintFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listview = (ListView)v.findViewById(R.id.iprint_listview);
        listview_iprints = new ArrayList<>();
        listview_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listview_iprints);
        listview.setAdapter(listview_adapter);

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
                listview_iprints.add(iprinttext);
                cursor.moveToNext();
            } while(!cursor.isLast());
        }

        final Spinner spinner_categories = (Spinner)v.findViewById(R.id.category_spinner);
        spinner_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

       Spinner spinner_buildings = (Spinner)v.findViewById(R.id.building_spinner);
        spinner_buildings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                building = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return v;
    }

}
