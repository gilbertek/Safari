package com.cafebits.safari.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cafebits.safari.R;
import com.cafebits.safari.activities.GalleryDetailActivity;
import com.cafebits.safari.adapters.GalleryImageAdapter;
import com.cafebits.safari.models.GalleryImage;
import com.cafebits.safari.services.SafariService;
import com.cafebits.safari.utils.Constant;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Gilbert on 10/28/15.
 */
public class GalleryFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static String TAG = GalleryFragment.class.getSimpleName();

    private GridView mGridView;
    private GalleryImageAdapter mAdapter;

    public static GalleryFragment getInstance() {
        GalleryFragment fragment = new GalleryFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate( R.layout.fragment_gallery_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView = (GridView) view.findViewById( R.id.grid );
        mGridView.setOnItemClickListener( this );
        mGridView.setDrawSelectorOnTop( true );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new GalleryImageAdapter( getActivity(), 0);
        mGridView.setAdapter( mAdapter );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.EXHIBITS_FEED)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SafariService service = retrofit.create(SafariService.class);


        Call<List<GalleryImage>> call = service.getGallery();
        Log.e(TAG, "Endpoint: " + call);
        call.enqueue(new Callback<List<GalleryImage>>() {
            @Override
            public void onResponse(Response<List<GalleryImage>> response, Retrofit retrofit) {
                int statusCode = response.code();
                Log.e(TAG, "Retrofit response code " + statusCode );

                if ( response.body().isEmpty() ) {
                    return;
                }

                for ( GalleryImage image : response.body() ) {
                    Log.e(TAG, image.getThumbnail());
                    mAdapter.add(image);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Safari:", "Retrofit error " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        GalleryImage image = (GalleryImage) parent.getItemAtPosition( position );
        Intent intent = new Intent( getActivity(), GalleryDetailActivity.class );
        intent.putExtra( GalleryDetailActivity.EXTRA_IMAGE, image.getImage() );
        intent.putExtra( GalleryDetailActivity.EXTRA_CAPTION, image.getCaption() );
        startActivity( intent );
    }
}
