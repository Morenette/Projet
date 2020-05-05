package com.example.homedemnage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    static final String BASE_URL = "https://raw.githubusercontent.com/Morenette/Api/master/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("app_projet", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        makeApiCall();
    }

    private void showList(List<Category> categoryList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListAdapter(categoryList);
        recyclerView.setAdapter(mAdapter);

    }

    private void makeApiCall(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            Api api = retrofit.create(Api.class);

            Call<RestCategoryResponse> call = api.getCategoryResponse();
            call.enqueue(new Callback<RestCategoryResponse>() {
                @Override
                public void onResponse(Call<RestCategoryResponse> call, Response<RestCategoryResponse> response) {
                    if (response.isSuccessful() &&  response.body() != null){
                        List<Category> categoryList = response.body().getResults();
                        saveList(categoryList);
                        showList(categoryList);
                    } else {
                        showError();
                    }
                }

                @Override
                public void onFailure(Call<RestCategoryResponse> call, Throwable t) {
                    showError();
                }
            });

    }

    private void saveList(List<Category> categoryList){
        String jsonString = gson.toJson(categoryList);

        sharedPreferences
                .edit()
                .putString("jsonCategoryList", jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}

