package com.example.emile1.findaparty.Activity.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.emile1.findaparty.Activity.MapStateManager;
import com.example.emile1.findaparty.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final int REQUEST_CHECK_SETTINGS = 33;
    protected static final String TAG = "SearchFragment";

    MapView mMapView;
    private GoogleMap googleMap;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FloatingActionButton mFloatinButton;
    private Circle circle;
    private LocationManager mlocManager;
    private LocationListener mlocListener;
    private final String ad = "39 rue marechal foch, 33200, Bordeaux, France";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mFloatinButton = (FloatingActionButton)v.findViewById(R.id.floatingButton);
        mMapView.onCreate(savedInstanceState);
        mlocManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {

        }
        mFloatinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLocationSettingsRequest(getContext());

            }
        });
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                MapStateManager mapStateManager = new MapStateManager(getContext());
                CameraPosition cameraPosition = mapStateManager.getSavedCameraPosition();
                if(cameraPosition != null ){
                    CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    googleMap.moveCamera(update);
                    googleMap.setMapType(mapStateManager.getSavedMapType());
//                    getLatLngFromAddress(ad);
                }
                getAllLans();
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("SEARCH","OK");
                    mFloatinButton.setEnabled(true);
                }
                else{
                    mFloatinButton.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MapStateManager mgr = new MapStateManager(getContext());
        //check if the map is null otherwise it crashes the first time we open the app
        if(googleMap !=null){
            mgr.saveMapState(googleMap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        getSimpleLocation();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(),REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"In the method");
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    private void listenForLocation (LocationListener locationListener){
        try{
            /* Use the LocationManager class to obtain GPS locations */
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER , 10000, 0, locationListener);
        } catch (SecurityException e){
            Log.i("SEARCH FRAGMENT", e.getMessage());
        }

    }

    /* Class My Location Listener, listen for location changes */
    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc)
        {
            // For dropping a marker at a point on the Map
            LatLng mPos = new LatLng(loc.getLatitude(), loc.getLongitude());
            setOptions(mPos);
            mlocManager.removeUpdates(mlocListener);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }

    // get the last known location or listen to a new location
    private void getSimpleLocation(){

        try{
            // STart a request Location update in case there is no last known location
            mlocListener = new MyLocationListener();
            // get the last know location from your location manager.
            Location networkLocation= mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location gpsLocation= mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // now get the lat/lon from the NETWORK PROVIDER and do something with it.
            if(networkLocation != null){
                Log.i("SEARCH","NETWORK");
                LatLng mPos = new LatLng(networkLocation.getLatitude(), networkLocation.getLongitude());
                setOptions(mPos);
            }
            //try with the gps provider
            else if (gpsLocation != null){
                Log.i("SEARCH","GPS");
                LatLng mPos = new LatLng(gpsLocation.getLatitude(), gpsLocation.getLongitude());
                setOptions(mPos);
            } else {
                //start listening for the user's position
                Log.i("SEARCH","listener");
                listenForLocation(mlocListener);
            }
        } catch (SecurityException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    // move the camera to the desired position and create a circle to show the location
    private void setOptions(LatLng latLng){
        if(circle!=null){
            circle.remove();
        }
        // For dropping a marker at a point on the Map
        CircleOptions circleOptions=new CircleOptions()
                .center(latLng)
                .strokeColor(Color.GRAY)
                .strokeWidth(2f)
                .fillColor(0x11FFA420)
                .radius(50);
        circle = googleMap.addCircle(circleOptions);

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    // geocoder blocks the UI, need to be done in a background thread
    private class GetLocationFromAddressTask extends AsyncTask<String, Void, LatLng>{

        protected LatLng doInBackground (String... address){
            Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
            LatLng latLngFromAddress = new LatLng(0,0);
            try
            {
                //geocoder returns a list of addresses (here 1 address)
                List<Address> addresses = geoCoder.getFromLocationName(address[0] , 1);
                if (addresses.size() > 0 && addresses!=null)
                {
                    double lat = addresses.get(0).getLatitude();
                    double lng = addresses.get(0).getLongitude();
                    latLngFromAddress = new LatLng(lat,lng);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return latLngFromAddress;
        }

        protected void onPostExecute(LatLng latLng){
            Log.d("Latitude", ""+latLng.latitude);
            Log.d("Longitude", ""+latLng.longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

    private String addressToString(JSONObject address) {
        String str = "";
        try {
            str = address.getString("lane")
                    + ", " + address.getString("zipcode")
                    + ", " + address.getString("city")
                    + ", " + address.getString("state");

        } catch (JSONException e) {
            Log.i("ADRESS",e.getMessage());
        }
        return str;
    }

    private void getAllLans(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for(ParseObject lan : objects)
                new GetLocationFromAddressTask().execute(addressToString(lan.getJSONObject("address")));

            }
        });
    }
}
