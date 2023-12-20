package com.example.tr2;

import com.example.tr2.Questions.QuestionEdited;
import com.example.tr2.Questions.QuestionsResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/ValidateLogin")
    Call<String> validateLogin(@Query("user") String user, @Query("password") String password);

    @GET("/api/getQuestions")
    Call<QuestionsResponse> getQuestions();

    @POST("/api/editQuestions/{id}")
    Call<Void> editQuestion(@Path("id") int id, @Body QuestionEdited question);

}
