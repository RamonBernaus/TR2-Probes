package com.example.tr2.Login;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tr2.ApiService;
import com.example.tr2.MainActivity;
import com.example.tr2.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainLogin extends AppCompatActivity {
    EditText User, Password;
    MainActivity MA = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User = findViewById(R.id.UserText);
        Password = findViewById(R.id.PasswordText);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Math");

            // Obtén el color desde resources
            int colorId = ContextCompat.getColor(this, R.color.casinavy);

            // Crea un ColorDrawable con el color obtenido
            ColorDrawable colorDrawable = new ColorDrawable(colorId);

            // Establece el ColorDrawable como fondo de la ActionBar
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        Button login_btn = findViewById(R.id.LoginBtn);
        login_btn.setOnClickListener(v -> {
            String user = User.getText().toString();
            String password = Password.getText().toString();
            CallLogin(user,password);
        });
    }

    public void CallLogin(String user, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MA.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<String> call = apiService.validateLogin(user, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                    Log.d("TAG", result);
                    if ("Verificado".equals(result)) {
                        Toast.makeText(MainLogin.this, "Usuari i Contrasenya Correctes", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainLogin.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainLogin.this, "Usuari i Contrasenya Incorrectes", Toast.LENGTH_SHORT).show();
                        Log.e("Noup", "No Verificado");
                    }
                } else {
                    // Manejar otros casos de respuesta aquí
                    Log.e("Error", "Respuesta no exitosa. Código: " + response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Log.e("Error", "Error de red: " + t.getMessage());
                } else {
                    Log.e("Error", "Error en la conversión de datos: " + t.getMessage());
                }
            }
        });
    }
}
