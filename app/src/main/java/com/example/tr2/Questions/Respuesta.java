package com.example.tr2.Questions;

import com.google.gson.annotations.SerializedName;

public class Respuesta {
    @SerializedName("id")
    private int id;

    @SerializedName("opcio")
    private String opcio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpcio() {
        return opcio;
    }

    public void setOpcio(String opcio) {
        this.opcio = opcio;
    }
}
