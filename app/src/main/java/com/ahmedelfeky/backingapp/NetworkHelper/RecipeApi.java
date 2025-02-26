package com.ahmedelfeky.backingapp.NetworkHelper;

import com.ahmedelfeky.backingapp.Model.RecipeResults;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface RecipeApi {
    @GET("baking.json")
    Flowable<List<RecipeResults>> getRecipeResults();
}
