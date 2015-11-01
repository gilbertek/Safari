package com.cafebits.safari.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafebits.safari.R;
import com.squareup.picasso.Picasso;

public class GalleryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "extra_image";
    public static final String EXTRA_CAPTION = "extra_caption";

    private TextView mCaptionTextView;
    private ImageView mimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCaptionTextView = (TextView) findViewById( R.id.caption );
        mimageView = (ImageView) findViewById( R.id.image );

        if ( getIntent() != null && getIntent().getExtras() != null ) {
            if ( getIntent().getExtras().containsKey( EXTRA_IMAGE ) ) {
                Picasso.with( this )
                        .load( getIntent().getExtras().getString( EXTRA_IMAGE ))
                        .into( mimageView );
            }
        }
    }


}
