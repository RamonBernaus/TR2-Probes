package com.example.tr2.Questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> preguntas;

    public QuestionsAdapter(List<Question> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question pregunta = preguntas.get(position);
        holder.enunciadoTextView.setText(pregunta.getEnunciat());
        // Puedes configurar más vistas según tus necesidades
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView enunciadoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            enunciadoTextView = itemView.findViewById(R.id.enunciadoTextView);
            // Configura más vistas según tus necesidades
        }
    }
}


