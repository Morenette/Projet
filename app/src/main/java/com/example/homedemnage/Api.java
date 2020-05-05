package com.example.homedemnage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/api/v2/pokemon/")
    Call<RestCategoryResponse> getCategoryResponse();

    @GET("/api/v2/ability")
    Call<RestCategoryResponse> getDetailResponse();
}
