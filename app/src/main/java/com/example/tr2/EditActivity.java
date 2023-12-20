package com.example.tr2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tr2.Questions.QuestionEdited;
import com.example.tr2.Questions.QuestionsResponse;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity {
    MainActivity MA = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        // Recupera los datos del Intent
        int id = getIntent().getIntExtra("ID_KEY", -1);
        String pregunta = getIntent().getStringExtra("PREGUNTA_KEY");
        List<String> respuestas = getIntent().getStringArrayListExtra("RESPUESTAS_KEY");

        Button Accept = findViewById(R.id.Aceptar);
        // Muestra los datos en EditTexts
        EditText preguntaEditText = findViewById(R.id.editTextPregunta);
        EditText respuestasEditText1 = findViewById(R.id.editTextRespuesta1);
        EditText respuestasEditText2 = findViewById(R.id.editTextRespuesta2);
        EditText respuestasEditText3 = findViewById(R.id.editTextRespuesta3);
        EditText respuestasEditText4 = findViewById(R.id.editTextRespuesta4);

        preguntaEditText.setText(pregunta);

        for (int i = 0; i < respuestas.size(); i++) {
            String respuesta = respuestas.get(i);
            switch (i) {
                case 0:
                    respuestasEditText1.setText(respuesta);
                    break;
                case 1:
                    respuestasEditText2.setText(respuesta);
                    break;
                case 2:
                    respuestasEditText3.setText(respuesta);
                    break;
                case 3:
                    respuestasEditText4.setText(respuesta);
                    break;
                default:
                    break;
            }
        }

        // Configura Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MA.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Accept.setOnClickListener(v->{
            Context context = this;
            String nuevaPregunta = preguntaEditText.getText().toString();
            List<String> nuevasRespuestas = Arrays.asList(
                    respuestasEditText1.getText().toString(),
                    respuestasEditText2.getText().toString(),
                    respuestasEditText3.getText().toString(),
                    respuestasEditText4.getText().toString()
            );

            QuestionEdited nuevaQuestion = new QuestionEdited(id, nuevaPregunta, nuevasRespuestas);

            Call<Void> call = apiService.editQuestion(id, nuevaQuestion);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        // La edición fue exitosa
                        Log.d("EditActivity", "Pregunta editada con éxito");
                        Toast toast = Toast.makeText(context, "Pregunta editada con éxito", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Error en la edición
                        Log.e("EditActivity", "Error al editar la pregunta. Código: " + response.code());
                        Toast toast = Toast.makeText(context, "Error al editar la pregunta.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    // Error en la conexión
                    Log.e("EditActivity", "Error de conexión: " + t.getMessage());
                    Toast toast = Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        });
    }
}


