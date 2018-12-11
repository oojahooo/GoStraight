package com.oojahooo.gostraight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import static com.oojahooo.gostraight.MainActivity.sectionBuilding;

public class MapFragment extends Fragment implements View.OnClickListener, MapView.MapViewEventListener, MapView.CurrentLocationEventListener {

    private FloatingActionButton fab;
    private MapView mapView;
    private double lat, lon;
    public static int mapPosition = -1;

    Cursor cursor;

    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        this.mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(this.mapView);

        cursor = MainActivity.mDb.rawQuery(GostraightDBCtruct.SQL_SELECT, null);

        mapView.removeAllPOIItems();

        int tempPosition = 0;
        if ((cursor != null && cursor.getCount() != 0)) {
            cursor.moveToFirst();
            do {
                int newcategory = cursor.getInt(1);
                String newbuilding = cursor.getString(2);
                if(category == 0 || (category != 0 && category == newcategory)) {
                    if(section == 0 || (section <= 8 && sectionBuilding.get(section).contains(newbuilding))) {
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
                        marker.setShowDisclosureButtonOnCalloutBalloon(false);
                        marker.setMapPoint(mapPoint);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                        this.mapView.addPOIItem(marker);
                        if(mapPosition >= 0 && tempPosition == mapPosition) {
                            mapView.selectPOIItem(marker, true);
                        }
                        tempPosition++;
                    }
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "위치권한을 받았습니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "위치권한이 거부되었습니다. 현위치 기능을 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
                }
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
        try {
            if (mapView.getCurrentLocationTrackingMode() != MapView.CurrentLocationTrackingMode.TrackingModeOff)
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        } catch (NullPointerException e) {
        }
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
