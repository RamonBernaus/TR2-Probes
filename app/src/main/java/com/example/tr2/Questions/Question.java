package com.example.tr2.Questions;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciat() {
        return enunciat;
    }

    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }

    public List<Respuesta> getRespostes() {
        return respostes;
    }

    public void setRespostes(List<Respuesta> respostes) {
        this.respostes = respostes;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("enunciat")
    private String enunciat;

    @SerializedName("respostes")
    private List<Respuesta> respostes;


}

