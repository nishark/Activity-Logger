package asu.ker.heatmappers.heatmappers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {
    private MapView mapView;
    private GoogleMap googleMap;
    private List latLongs = new ArrayList<LatLng>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.maplayout, container, false);
        mapView = rootview.findViewById(R.id.mapView);


        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                Log.d("MapView custom message", "I am here");
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    googleMap.setMyLocationEnabled(true);

                }
                ParseQuery<ParseObject> queryobj = ParseQuery.getQuery("Logs");
                queryobj.orderByDescending("createdAt");
                queryobj.fromLocalDatastore();
                queryobj.setLimit(100);
                queryobj.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        Log.d("MapView custom message","Inside Level 1");
                        if (e == null) {

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            Log.d("MapView custom message","Checking if the null condition passes");
                            Log.d("MapView custom message","Let's print the log list size "+objects.size());
                            for (int i=0; i<objects.size(); i++) {
                                Log.d("MapView custom message","Inside Level 2");
                                LatLng pos = new LatLng(objects.get(i).getDouble("latitude"), objects.get(i).getDouble("longitude"));
                                latLongs.add(pos);
                                builder.include(pos);
                                Log.d("Custom Map Lat Long","Lat: "+objects.get(i).getDouble("latitude")+" Long: "+objects.get(i).getDouble("longitude"));
                                String dateString = objects.get(i).getString("time") + " " + objects.get(i).getString("date");

                                Marker marker = googleMap.addMarker(new MarkerOptions().position(pos).title(objects.get(i).getString("activity")).snippet(dateString));
                                //marker.showInfoWindow();
                            }

                            if (objects.size() > 0) {
                                //Toast.makeText(getActivity(), String.valueOf(logList.size()), Toast.LENGTH_LONG).show();
                                Log.d("MapView custom message","Inside Level 3");
                                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions().addAll(latLongs));

                                LatLngBounds bounds = builder.build();
                                int width = getResources().getDisplayMetrics().widthPixels;
                                int height = getResources().getDisplayMetrics().heightPixels;
                                int padding = (int) (width * 0.20); // offset from edges of the map 10% of screen

                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                                googleMap.moveCamera(cu);


                            }


                        } else {
                            Log.d("MapView custom message","Inside Level 4");
                            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

                return rootview;

    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
