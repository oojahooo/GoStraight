package com.oojahooo.gostraight;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListIprintFragment extends Fragment {
    List<String> listview_iprints;
    ArrayAdapter<String> listview_adapter;

    public ListIprintFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listview = (ListView)v.findViewById(R.id.iprint_listview);
        listview_iprints = new ArrayList<>();
        listview_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listview_iprints);
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview_iprints.add("Hello");
        listview_iprints.add("World");
        listview_iprints.add("Kim");
        listview_iprints.add("Jaeho");
        listview.setAdapter(listview_adapter);
        return v;
    }

}
