package com.example.tr2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.Questions.Question;
import com.example.tr2.Questions.QuestionsAdapter;
import com.example.tr2.Questions.Respuesta;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionsAdapter preguntasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Question>> call = apiService.getQuestions();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    List<Question> preguntas = response.body();
                    // Ahora puedes usar la lista de preguntas como desees
                } else {
                    // Manejar errores de respuesta
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                // Manejar errores de red
            }
        });

    }
}

