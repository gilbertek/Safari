package com.cafebits.safari.services;

import com.cafebits.safari.models.Animal;
import com.cafebits.safari.models.Flower;
import com.cafebits.safari.models.GalleryImage;
import com.cafebits.safari.models.Pin;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Gilbert on 10/31/15.
 */
public interface SafariService {

    @GET( "/exhibits.json" )
    Call<List<Animal>> getAnimals();

    @GET( "/feeds/flowers.json" )
    Call<List<Flower>>getFlowers();

    @GET( "/gallery.json" )
    Call<List<GalleryImage>>getGallery();

    @GET( "/pins.json" )
    Call<List<Pin>>getPins();
}
