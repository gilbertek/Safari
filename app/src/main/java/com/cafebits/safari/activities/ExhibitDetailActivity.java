package com.cafebits.safari.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafebits.safari.R;
import com.cafebits.safari.models.Animal;
import com.squareup.picasso.Picasso;

public class ExhibitDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ANIMAL = "extra_animal";
    public static String TAG = ExhibitDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animal animal = getIntent().getExtras().getParcelable( EXTRA_ANIMAL );
        Log.i(TAG, "Animal:: " + animal.getName() );

        TextView species = (TextView) findViewById( R.id.species );
        TextView description = (TextView) findViewById( R.id.description );
        ImageView image = (ImageView) findViewById( R.id.image );

        species.setText( animal.getSpecies() );
        description.setText( animal.getDescription() );

        Picasso.with( this ).load( animal.getImage() ).into( image );
    }

}
