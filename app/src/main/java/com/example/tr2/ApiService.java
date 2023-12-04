package com.example.tr2;

import com.example.tr2.Questions.QuestionsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/ValidateLogin")
    Call<String> validateLogin(@Query("user") String user, @Query("password") String password);

    @GET("/api/getQuestions")
    Call<QuestionsResponse> getQuestions();
}
