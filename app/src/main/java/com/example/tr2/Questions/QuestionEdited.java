package com.example.tr2.Questions;

import java.util.List;

public class QuestionEdited {
    private int id;
    private String pregunta;
    private List<String> respuestas;

    public QuestionEdited(int id, String pregunta, List<String> respuestas) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuestas = respuestas;
    }
}

