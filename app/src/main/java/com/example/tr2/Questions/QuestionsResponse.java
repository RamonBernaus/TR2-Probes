package com.example.tr2.Questions;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class QuestionsResponse {
    @SerializedName("preguntes")
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public static class Question {
        @SerializedName("id")
        private int id;

        @SerializedName("enunciat")
        private String enunciat;

        @SerializedName("respostes")
        private List<Answer> respostes;

        public int getId() {
            return id;
        }

        public String getEnunciat() {
            return enunciat;
        }

        public List<Answer> getRespostes() {
            return respostes;
        }
    }

    public static class Answer {
        @SerializedName("id")
        private int id;

        @SerializedName("opcio")
        private String opcio;

        public int getId() {
            return id;
        }

        public String getOpcio() {
            return opcio;
        }
    }
}


