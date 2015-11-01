package com.cafebits.safari.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.cafebits.safari.activities.ExhibitDetailActivity;
import com.cafebits.safari.adapters.ExhibitsAdapter;
import com.cafebits.safari.models.Animal;
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
public class ExhibitsListFragment extends ListFragment {

    public static String TAG = ExhibitsListFragment.class.getSimpleName();

    private ExhibitsAdapter mAdapter;

    public static ExhibitsListFragment getInstance() {
        ExhibitsListFragment fragment = new ExhibitsListFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListShown(false);

        mAdapter = new ExhibitsAdapter( getActivity(), 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.EXHIBITS_FEED)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SafariService service = retrofit.create(SafariService.class);

        Call<List<Animal>> call = service.getAnimals();
        Log.e(TAG, "Endpoint: " + call );
        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Response<List<Animal>> response, Retrofit retrofit) {
                int statusCode = response.code();
                Log.e(TAG, "Retrofit response code " + statusCode );

                if ( response.body().isEmpty() ) {
                    return;
                }

                for ( Animal animal : response.body() ) {
                    mAdapter.add( animal );
                }

                mAdapter.notifyDataSetChanged();
                setListAdapter( mAdapter );
                setListShown( true );
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Safari:", "Retrofit error " + t.getMessage());
            }
        });

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent( getActivity(), ExhibitDetailActivity.class );
        intent.putExtra( ExhibitDetailActivity.EXTRA_ANIMAL, mAdapter.getItem( position ) );

        startActivity( intent );
    }
}