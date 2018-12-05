package com.oojahooo.gostraight;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListIprintFragment extends Fragment {
    ArrayAdapter<String> listview_adapter;

    public ListIprintFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listview = (ListView)v.findViewById(R.id.iprint_listview);

        listview_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, MainActivity.listview_iprints);
        listview.setAdapter(listview_adapter);


        return v;
    }

}
