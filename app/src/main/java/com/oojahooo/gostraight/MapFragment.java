package com.oojahooo.gostraight;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import static com.oojahooo.gostraight.MainActivity.ATM;
import static com.oojahooo.gostraight.MainActivity.IPRINT;
import static com.oojahooo.gostraight.MainActivity.VENDING;
import static com.oojahooo.gostraight.MainActivity.WATER;
import static com.oojahooo.gostraight.MainActivity.category;
import static com.oojahooo.gostraight.MainActivity.section;

public class MapFragment extends Fragment implements View.OnClickListener, MapView.MapViewEventListener, MapView.CurrentLocationEventListener {

    private FloatingActionButton fab;
    private MapView mapView;
    private double lat, lon;

    Cursor cursor;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Spinner 연동
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        this.mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(this.mapView);

        cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

        mapView.removeAllPOIItems();

        if ((cursor != null && cursor.getCount() != 0)) {
            cursor.moveToFirst();
            do {
                int newcategory = cursor.getInt(1);
                if(category == 0 || (category != 0 && category == newcategory)) {
                    lat = cursor.getDouble(4);
                    lon = cursor.getDouble(5);
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon);
                    MapPOIItem marker = new MapPOIItem();
                    switch (newcategory) {
                        case IPRINT:
                            marker.setItemName("아이프린트");
                            break;
                        case WATER:
                            marker.setItemName("정수기");
                            break;
                        case VENDING:
                            marker.setItemName("자판기");
                            break;
                        case ATM:
                            marker.setItemName("ATM");
                            break;
                    }
                    marker.setMapPoint(mapPoint);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    this.mapView.addPOIItem(marker);
                }
                cursor.moveToNext();
            } while (!cursor.isLast());
        }

            this.mapView.setMapViewEventListener(this);

            fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
            fab.setOnClickListener(this);
            return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.floatingActionButton) {
            Toast.makeText(getActivity(), "현위치를 탐색합니다.", Toast.LENGTH_LONG).show();
            try {
                this.mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            } catch (NullPointerException e) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }

        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.5864371, 127.029278);
        this.mapView.setMapCenterPoint(mapPoint, true);
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } catch(NullPointerException e) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {

            }
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapCenterPoint) {
        this.lat = mapCenterPoint.getMapPointGeoCoord().latitude;
        this.lon = mapCenterPoint.getMapPointGeoCoord().longitude;
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {}

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onCurrentLocationUpdate(final MapView mapView, final MapPoint currentLocation, float assuracyInMeters) {
        this.lat = currentLocation.getMapPointGeoCoord().latitude;
        this.lon = currentLocation.getMapPointGeoCoord().longitude;
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {}

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float headingAngle) {}

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {}

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {}
}
