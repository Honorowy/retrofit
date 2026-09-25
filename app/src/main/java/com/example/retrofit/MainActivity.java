package com.example.retrofit;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pytanie> pytania;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // https://my-json-server.typicode.com/Honorowy/pytania_retrofit/
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/Honorowy/pytania_retrofit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<ArrayList<Pytanie>> call = jsonPlaceHolderApi.getpytania();
        call.enqueue(
                new Callback<ArrayList<Pytanie>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Pytanie>> call, Response<ArrayList<Pytanie>> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pytania = response.body();
                        Toast.makeText(MainActivity.this, pytania.get(0).trescPytanie, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Pytanie>> call, Throwable t) {

                    }
                }
        );
    }
}