package com.cafebits.safari.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cafebits.safari.R;
import com.cafebits.safari.models.GalleryImage;
import com.squareup.picasso.Picasso;

/**
 * Created by Gilbert on 11/1/15.
 */
public class GalleryImageAdapter extends ArrayAdapter<GalleryImage> {
    public GalleryImageAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if ( convertView == null ) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from( getContext() )
                    .inflate( R.layout.view_gallery_thumbnail, parent, false );

            holder.imageView = (ImageView) convertView.findViewById( R.id.image );
            convertView.setTag( holder );

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with( getContext() )
                .load( getItem( position ).getThumbnail() ).into( holder.imageView);

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
