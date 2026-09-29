package com.example.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pytanie> pytania;
    TextView textViewPytanie;
    RadioGroup radioGroupPytania;
    RadioButton radioButtonA, radioButtonB, radioButtonC;
    Button buttonNastepne;
    List<Pytanie> pytaniaZInterentu;
    int nrPytania = 0, aktualnyIndeks = 0;
    boolean czyPokazujeWynik;

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
        textViewPytanie = findViewById(R.id.textViewPytanie);
        radioButtonA = findViewById(R.id.radioButtonA);
        radioButtonB = findViewById(R.id.radioButtonB);
        radioButtonC = findViewById(R.id.radioButtonC);
        radioGroupPytania = findViewById(R.id.radioGroup);
        buttonNastepne = findViewById(R.id.buttonNastepne);
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
                        if (pytaniaZInterentu != null && !pytaniaZInterentu.isEmpty()){
                            wyswietlPytanie(0);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Pytanie>> call, Throwable t) {

                    }
                }
        );
        buttonNastepne.setOnClickListener(view -> sprawdzOdpowiedz());
    }
    private void wyswietlPytanie(int x){
        if (x < 0 || x <= pytaniaZInterentu.size()){
            textViewPytanie.setText("Wynik to:");
        }
        aktualnyIndeks = x;
        czyPokazujeWynik = false;

        Pytanie pytanie = pytaniaZInterentu.get(x);
        textViewPytanie.setText(pytanie.trescPytanie);

        radioButtonA.setText(pytanie.odpA);
        radioButtonB.setText(pytanie.odpB);
        radioButtonC.setText(pytanie.odpC);

        radioGroupPytania.clearCheck();

        for (int i = 0; i < radioGroupPytania.getChildCount(); i++){
            radioGroupPytania.getChildAt(i).setEnabled(true);
        }
    }
    private void sprawdzOdpowiedz(){
        if (pytaniaZInterentu == null || aktualnyIndeks >= pytaniaZInterentu.size()){
            return;
        }
        if (czyPokazujeWynik){
            wyswietlPytanie(aktualnyIndeks + 1);
            return;
        }
        int wybor = radioGroupPytania.getCheckedRadioButtonId();
        if (wybor == -1){
            Toast.makeText(this, "Wybierz odpowiedź", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton zaprzycisk = findViewById(wybor);
        int wyodp = radioGroupPytania.indexOfChild(zaprzycisk);

        Pytanie pytanie = pytaniaZInterentu.get(aktualnyIndeks);
        int poodp = pytanie.odpowiedzPoprawna;

        RadioButton poprzy = (RadioButton) radioGroupPytania.getChildAt(poodp);


    }
}