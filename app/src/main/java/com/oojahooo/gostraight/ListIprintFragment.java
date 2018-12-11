package com.oojahooo.gostraight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.oojahooo.gostraight.MainActivity.ATM;
import static com.oojahooo.gostraight.MainActivity.IPRINT;
import static com.oojahooo.gostraight.MainActivity.MAP_FRAGMENT;
import static com.oojahooo.gostraight.MainActivity.VENDING;
import static com.oojahooo.gostraight.MainActivity.WATER;
import static com.oojahooo.gostraight.MainActivity.category;
import static com.oojahooo.gostraight.MainActivity.dialog_iprints;
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

        listview_iprints.clear();
        dialog_iprints.clear();
        String iprinttext;

        if((cursor != null && cursor.getCount() != 0)) {
            cursor.moveToFirst();
            do {
                iprinttext = "";
                int newcategory = cursor.getInt(1);
                String newbuilding = cursor.getString(2);

                if(category == 0 || (category != 0 && category == newcategory)) {
                    if(section == 0 || (section <= 8 && sectionBuilding.get(section).contains(newbuilding))) {
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
                        listview_iprints.add(iprinttext);
                        dialog_iprints.add(cursor.getString(3));
                    }
                }
                cursor.moveToNext();
            } while(!cursor.isLast());
        }

        listview.setOnItemClickListener(new ListViewItemClickListener());

        return v;
    }

    class ListViewItemClickListener implements AdapterView

            .OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int thisposition = position;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setPositiveButton(R.string.button_to_map, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MapFragment.mapPosition = thisposition;
                    MainActivity.FRAGMENT_STATE = MAP_FRAGMENT;
                    ((MainActivity)getActivity()).callFragment(MainActivity.MAP_FRAGMENT);
                }
            });

            alertDlg.setMessage(dialog_iprints.get(position));
            alertDlg.show();
        }
    }

}
