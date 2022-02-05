package com.lk.onlinebookshopcustomer.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lk.onlinebookshopcustomer.DirectionLib.FetchURL;
import com.lk.onlinebookshopcustomer.GPS.GPSHelper;
import com.lk.onlinebookshopcustomer.POJO.MapDistanceObject;
import com.lk.onlinebookshopcustomer.POJO.MapTimeObject;
import com.lk.onlinebookshopcustomer.R;
import com.lk.onlinebookshopcustomer.UI.ContactActivity;
import com.lk.onlinebookshopcustomer.UI.HomeActivity;


public class GoogleMapsFragment extends Fragment {

    LatLng customerlocation;
    LatLng destinationlocation;
    FirebaseFirestore db;
    private int LOCATION_PERMISSION = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public GoogleMap currentGoogleMap;
    private String TAG = "Goooooooooogle Maaaaaaaaaappppppppppp";
    private Polyline currentPoliline;
    public Marker currentMarker;
    public Marker riderMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GoogleMapsFragment.super.getContext());
            currentGoogleMap = googleMap;
            updateCustomerLocation();


        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION) {
            if (permissions.length > 0) {
            }
            updateCustomerLocation();
        }
    }


    private void updateCustomerLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    Toast.makeText(GoogleMapsFragment.super.getContext(), "Location" + location.getLongitude() + "" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                    customerlocation = new LatLng(location.getLatitude(), location.getLongitude());
                    destinationlocation = new LatLng(6.947807116234407, 79.88035859268027);// 6.947807116234407, 79.88035859268027
//                    destinationlocation = new LatLng(7.0336, 79.9351);


//                    currentGoogleMap.addMarker(new MarkerOptions().position(customerlocation).title("I'm here"));

//                    BitmapDescriptor ico_current= BitmapDescriptorFactory.fromResource(R.drawable.ic_tracking);
//                    BitmapDescriptor ico_destination= BitmapDescriptorFactory.fromResource(R.drawable.ic_walkto);
                    BitmapDescriptor ico_current = getBitmapDesc(getActivity(), R.drawable.ic_walkto);
                    BitmapDescriptor ico_destination = getBitmapDesc(getActivity(), R.drawable.ic_tracking);

                    MarkerOptions current_location_marker = new MarkerOptions().icon(ico_current).draggable(true).position(customerlocation).title("I'm here");
                    MarkerOptions destination_location_marker = new MarkerOptions().icon(ico_destination).draggable(false).position(destinationlocation).title("AR Book Shop");

                    currentMarker = currentGoogleMap.addMarker(current_location_marker);
                    currentGoogleMap.addMarker(destination_location_marker);

                    currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(customerlocation));
                    currentGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));


                    currentGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {
                            Log.d(TAG, "DragStarted");
                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                            Log.d(TAG, "Drag between");
                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {

                            Log.d(TAG, "Dragend");
                            customerlocation = marker.getPosition();
//                            LatLng latLng = marker.getPosition();
//                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                            try {
//                                android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
//                                ((MapAddressActivity) getActivity()).setAddress(address);
//                            } catch (Exception e) {
//
//                            }
//                            ((MapAddressActivity) getActivity()).setJobLatLang(customerlocation, destinationlocation);
//                          asynctask eken url akata call krnva call karama apee data enva

                            new FetchURL() {

                                @Override
                                public void onTaskDone(Object... values) {

                                    if (currentPoliline != null) {
                                        currentPoliline.remove();

                                    }
                                    currentPoliline = currentGoogleMap.addPolyline((PolylineOptions) values[0]);
                                }

                                @Override
                                public void onDistanceTaskDone(MapDistanceObject distance) {
//                                    Toast.makeText(getActivity(), distance.getDistanceText(), Toast.LENGTH_SHORT).show();
//                                    double startPrice = 50;
//                                    double aditionalPricePerKm = 40;
////                                    ena distance eken 1km arala ithuru tika gannva
//                                    double adtionalm = distance.getDistanceValM() - 1000;
////                                    A ena Meter gaana Km krnva
//                                    double adtionalPrice = ((int) (adtionalm / 1000)) * aditionalPricePerKm;
//                                    double estimatedPrice = startPrice + adtionalPrice;
//                                    Toast.makeText(getActivity(), "Your Estimated Price is: " + estimatedPrice, Toast.LENGTH_SHORT).show();
                                    ((ContactActivity) getActivity()).setDistance(String.valueOf(distance.getDistanceValM()));
                                }

                                @Override
                                public void onTimeTaskDone(MapTimeObject time) {
                                    ((ContactActivity) getActivity()).setTime(time.getTimeInText());

                                }


                            }.execute(getUrl(customerlocation, destinationlocation, "driving"), "driving");


                        }
                    });
                } else {
                    Toast.makeText(GoogleMapsFragment.super.getContext(), "Location Not Found", Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GoogleMapsFragment.super.getContext(), "Location Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private BitmapDescriptor getBitmapDesc(FragmentActivity activity, int ic_tracking) {

        Drawable LAYER_1 = ContextCompat.getDrawable(activity, ic_tracking);
        LAYER_1.setBounds(0, 0, LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LAYER_1.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        GPSHelper gpsHelper = new GPSHelper(this);
        gpsHelper.getCurrentLocationListner(getContext());

    }

    //url aka hadagnna
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        Log.d(TAG, "URL:" + url);
        return url;
    }

//


}

