package com.example.tr2.Questions;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tr2.ApiService;
import com.example.tr2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainQuestions extends AppCompatActivity {
    private static final String BASE_URL = "http://192.168.205.83:3000/"; // Cambia esto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crea la instancia de la interfaz ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Realiza la solicitud GET
        Call<QuestionsResponse> call = apiService.getQuestions();
        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsResponse> call, @NonNull Response<QuestionsResponse> response) {
                if (response.isSuccessful()) {
                    QuestionsResponse questionsResponse = response.body();
                    // Accede a las preguntas y respuestas aquí
                    List<Question> preguntas = questionsResponse.getPreguntas();
                    // Haz lo que necesites con los datos
                } else {
                    // Manejar errores aquí
                    Log.e("Error", "Respuesta no exitosa. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<QuestionsResponse> call, Throwable t) {
                // Manejar errores de red aquí
                Log.e("Error", "Error de red: " + t.getMessage());
            }
        });
    }
}

