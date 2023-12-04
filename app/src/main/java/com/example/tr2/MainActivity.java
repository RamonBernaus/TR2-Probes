package com.example.tr2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.Questions.QuestionsAdapter;
import com.example.tr2.Questions.QuestionsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.205.83:3000/") // Reemplaza con la dirección y puerto correctos
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<QuestionsResponse> call = apiService.getQuestions();
        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(Call<QuestionsResponse> call, Response<QuestionsResponse> response) {
                if (response.isSuccessful()) {
                    QuestionsResponse questionsResponse = response.body();
                    if (questionsResponse != null) {
                        // Imprime los datos recibidos para verificar
                        Log.d("MainActivity", "Data received: " + questionsResponse.toString());

                        // Configurar el RecyclerView con las preguntas
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questionsResponse.getQuestions());
                        recyclerView.setAdapter(questionsAdapter);
                    } else {
                        Log.e("MainActivity", "Response body is null");
                    }
                } else {
                    Log.e("MainActivity", "Unsuccessful response. Code: " + response.code());
                    // Manejo de errores específicos según el código de respuesta
                }
            }



            @Override
            public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                // Aquí manejas un fallo en la comunicación
            }
        });

    }
}

