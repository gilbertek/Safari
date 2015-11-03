package com.cafebits.safari.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cafebits.safari.R;
import com.cafebits.safari.models.Animal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Gilbert on 10/29/15.
 */
public class ExhibitsAdapter extends ArrayAdapter<Animal> {

    public ExhibitsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if ( convertView == null ) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from( getContext() ).inflate(R.layout.view_exhibit_list_item, parent, false);

            holder.progressBar = (ProgressBar) convertView.findViewById( R.id.progress );
            holder.name = (TextView) convertView.findViewById( R.id.name );
            holder.species = (TextView) convertView.findViewById( R.id.species );
            holder.thumbnail = (ImageView) convertView.findViewById( R.id.thumbnail );

            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.thumbnail.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.name.setText( getItem( position ).getName() );
        holder.species.setText(getItem(position).getSpecies());

        final ViewHolder tmp = holder;

        Picasso.with(getContext() )
                .load( getItem( position )
                .getThumbnail() )
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        tmp.progressBar.setVisibility( View.GONE );
                        tmp.thumbnail.setVisibility( View.VISIBLE );
                    }

                    @Override
                    public void onError() {
                        tmp.progressBar.setVisibility( View.GONE );

                    }
                });

        return convertView;
    }

    class ViewHolder {
        ImageView thumbnail;
        TextView name;
        TextView species;
        ProgressBar progressBar;
    }
}
