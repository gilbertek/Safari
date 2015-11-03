package com.cafebits.safari.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cafebits.safari.models.Pin;
import com.cafebits.safari.services.SafariService;
import com.cafebits.safari.utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Gilbert on 10/28/15.
 */
public class SafariMapFragment extends SupportMapFragment {

    public static String TAG = SafariMapFragment.class.getSimpleName();


    public static SafariMapFragment getInstance() {
        SafariMapFragment fragment = new SafariMapFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( 39.7500, -104.9500 ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
        getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        getMap().setTrafficEnabled(true);

        getMap().getUiSettings().setZoomControlsEnabled(true);

        getMap().setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                marker.showInfoWindow();
                return true;
            }
        });

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng( 39.7500, -104.9500 ) );

        options.title(" Zoo");
        options.icon( BitmapDescriptorFactory
                .defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );

        getMap().addMarker( options );


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.EXHIBITS_FEED)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SafariService service = retrofit.create(SafariService.class);


        Call<List<Pin>> call = service.getPins();

        call.enqueue(new Callback<List<Pin>>() {
            @Override
            public void onResponse(Response<List<Pin>> response, Retrofit retrofit) {
                int statusCode = response.code();
                Log.e( TAG, "Retrofit response code " + statusCode );

                if ( response.body().isEmpty() || !isAdded() ) {
                    return;
                }

                for ( Pin pin : response.body() ) {
                    Log.e( TAG, pin.getName() );

                    MarkerOptions options = new MarkerOptions()
                            .position( new LatLng(pin.getLatitude(), pin.getLongitude() ) );

                    options.title( pin.getName() );
                    options.icon( BitmapDescriptorFactory
                            .defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );

                    getMap().addMarker( options );

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e( TAG, "Retrofit error " + t.getMessage() );
            }
        });
    }
}
