package com.cafebits.safari.fragments;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Gilbert on 10/28/15.
 */
public class SafariMapFragment extends SupportMapFragment {

    public static SafariMapFragment getInstance() {
        SafariMapFragment fragment = new SafariMapFragment();

        return fragment;
    }
}
