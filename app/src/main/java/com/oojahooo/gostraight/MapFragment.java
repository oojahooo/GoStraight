package com.oojahooo.gostraight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {
    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup)v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        return v;
    }
}
