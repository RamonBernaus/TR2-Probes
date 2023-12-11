package com.example.tr2.Questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    private final List<QuestionsResponse.Question> questions;

    public QuestionsAdapter(List<QuestionsResponse.Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        QuestionsResponse.Question question = questions.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEnunciat;
        RecyclerView recyclerViewRespuestas;
        Button buttonEditar;
        Button buttonEliminar;


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEnunciat = itemView.findViewById(R.id.textViewEnunciat);
            recyclerViewRespuestas = itemView.findViewById(R.id.recyclerViewRespuestas);
            buttonEditar = itemView.findViewById(R.id.buttonEditar);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);

            buttonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            buttonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        public void bind(QuestionsResponse.Question question) {
            textViewEnunciat.setText(question.getEnunciat());

            // Si la pregunta tiene respuestas, configurar el RecyclerView interno con las respuestas
            if (question.getRespostes() != null) {
                recyclerViewRespuestas.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                AnswersAdapter answersAdapter = new AnswersAdapter(question.getRespostes());
                recyclerViewRespuestas.setAdapter(answersAdapter);
            }
        }
    }
}






