package com.oojahooo.gostraight;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.oojahooo.gostraight.MainActivity.ATM;
import static com.oojahooo.gostraight.MainActivity.IPRINT;
import static com.oojahooo.gostraight.MainActivity.VENDING;
import static com.oojahooo.gostraight.MainActivity.WATER;
import static com.oojahooo.gostraight.MainActivity.category;
import static com.oojahooo.gostraight.MainActivity.listview_iprints;
import static com.oojahooo.gostraight.MainActivity.section;
import static com.oojahooo.gostraight.MainActivity.sectionBuilding;

public class ListIprintFragment extends Fragment {

    ArrayAdapter<String> listview_adapter;
    Cursor cursor;

    public ListIprintFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listview = (ListView)v.findViewById(R.id.iprint_listview);

        listview_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listview_iprints);
        listview.setAdapter(listview_adapter);

        cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

        if(category != 0 || section != 0) {
            listview_iprints.clear();
            String iprinttext;

            if((cursor != null && cursor.getCount() != 0)) {
                cursor.moveToFirst();
                do {
                    iprinttext = "";
                    int newcategory = cursor.getInt(1);

                    if(category == 0 || (category != 0 && category == newcategory)) {
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
                        iprinttext += cursor.getString(2) + ", " + cursor.getString(3);
                        listview_iprints.add(iprinttext);
                    }
                    cursor.moveToNext();
                } while(!cursor.isLast());
            }
        }

        return v;
    }

}
