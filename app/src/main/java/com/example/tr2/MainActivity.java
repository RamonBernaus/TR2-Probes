package com.example.tr2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.Questions.QuestionsAdapter;
import com.example.tr2.Questions.QuestionsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //public String URL = "http://192.168.17.165:3000/";
    public String URL = "http://10.2.2.83:3000/";
    //public String URL = "http://192.168.17.0:3000/";
    private List<QuestionsResponse.Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Math");

            int colorId = ContextCompat.getColor(this, R.color.casinavy);

            ColorDrawable colorDrawable = new ColorDrawable(colorId);

            actionBar.setBackgroundDrawable(colorDrawable);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<QuestionsResponse> call = apiService.getQuestions();
        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsResponse> call, @NonNull Response<QuestionsResponse> response) {
                if (response.isSuccessful()) {
                    QuestionsResponse questionsResponse = response.body();
                    if (questionsResponse != null) {
                        // Assign the questions from the response to the class-level variable
                        questions = questionsResponse.getQuestions();

                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions);
                        recyclerView.setAdapter(questionsAdapter);
                        questionsAdapter.setOnItemClickListener(new QuestionsAdapter.OnItemClickListener() {
                            @Override
                            public void onEditClick(int position) {
                                // Ensure 'questions' is initialized before accessing it
                                if (questions != null && position < questions.size()) {
                                    // Obtén la pregunta y respuestas según la posición
                                    QuestionsResponse.Question selectedQuestion = questions.get(position);

                                    // Puedes acceder a los datos así:
                                    int id = selectedQuestion.getId();
                                    String pregunta = selectedQuestion.getEnunciat();
                                    List<QuestionsResponse.Answer> respuestas = selectedQuestion.getRespostes();

                                    // Convertir la lista de respuestas a una lista de strings
                                    List<String> respuestasStrings = convertAnswerListToStringList(respuestas);

                                    // Crea un Intent y agrégale datos
                                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                                    intent.putExtra("ID_KEY", id);
                                    intent.putExtra("PREGUNTA_KEY", pregunta);
                                    intent.putStringArrayListExtra("RESPUESTAS_KEY", new ArrayList<>(respuestasStrings));
                                    startActivity(intent);
                                }
                            }

                            // Método para convertir la lista de respuestas a una lista de strings
                            private List<String> convertAnswerListToStringList(List<QuestionsResponse.Answer> answers) {
                                List<String> answerStrings = new ArrayList<>();
                                for (QuestionsResponse.Answer answer : answers) {
                                    answerStrings.add(answer.getOpcio());
                                }
                                return answerStrings;
                            }


                            @Override
                            public void onDeleteClick(int position) {
                                Log.d("MainActivity", "Delete button clicked at position: " + position);
                            }
                        });

                    } else {
                        Log.e("MainActivity", "Response body is null");
                    }

                } else {
                    Log.e("MainActivity", "Unsuccessful response. Code: " + response.code());
                    // Manejo de errores específicos según el código de respuesta
                }
            }




            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
                // Aquí manejas un fallo en la comunicación
            }
        });

    }
}


