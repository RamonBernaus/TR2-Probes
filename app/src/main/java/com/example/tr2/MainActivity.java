package com.example.tr2;

import static com.example.tr2.Questions.QuestionsAdapter.setEditingMode;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //public String URL = "http://192.168.19.168:3000/";
    public String URL ="http://192.168.17.165:3000/";

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
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questionsResponse.getQuestions());
                        recyclerView.setAdapter(questionsAdapter);
                        questionsAdapter.setOnItemClickListener(new QuestionsAdapter.OnItemClickListener() {
                            @Override
                            public void onEditClick(int position) {
                                Log.d("MainActivity", "Edit button clicked at position: " + position);
                                setEditingMode(true);
                                questionsAdapter.notifyDataSetChanged();
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

                }
            }



            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
                Log.e("Error", "Error en la peticio");
            }
        });

    }
}

