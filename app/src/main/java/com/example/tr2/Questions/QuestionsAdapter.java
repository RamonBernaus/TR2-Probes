package com.example.tr2.Questions;

import static com.example.tr2.Questions.QuestionsAdapter.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tr2.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

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
        QuestionsAdapter.listener = listener;
    }

    private static boolean isEditing = false;

    public static void setEditingMode(boolean editing) {
        isEditing = editing;
    }


    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEnunciat;
        EditText editTextEnunciat;
        RecyclerView recyclerViewRespuestas;
        Button buttonEditar;
        Button buttonEliminar;



        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextEnunciat = itemView.findViewById(R.id.editTextEnunciat);
            textViewEnunciat = itemView.findViewById(R.id.textViewEnunciat);
            recyclerViewRespuestas = itemView.findViewById(R.id.recyclerViewRespuestas);
            buttonEditar = itemView.findViewById(R.id.buttonEditar);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);

            buttonEditar.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            });

            buttonEliminar.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
        public void bind(QuestionsResponse.Question question) {
            Log.d("QuestionViewHolder", "isEditing: " + isEditing);

            if (isEditing) {
                textViewEnunciat.setVisibility(View.GONE);
                editTextEnunciat.setVisibility(View.VISIBLE);
                buttonEditar.setVisibility(View.GONE);
            } else {
                textViewEnunciat.setVisibility(View.VISIBLE);
                editTextEnunciat.setVisibility(View.GONE);
                buttonEditar.setVisibility(View.VISIBLE);
            }

            // Si la pregunta tiene respuestas, configurar el RecyclerView interno con las respuestas
            if (question.getRespostes() != null) {
                recyclerViewRespuestas.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                AnswersAdapter answersAdapter = new AnswersAdapter(question.getRespostes());
                recyclerViewRespuestas.setAdapter(answersAdapter);
            }
        }
    }
}



