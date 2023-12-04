package com.example.tr2.Questions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder> {

    private List<QuestionsResponse.Answer> answers;

    public AnswersAdapter(List<QuestionsResponse.Answer> answers) {
        this.answers = answers;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        QuestionsResponse.Answer answer = answers.get(position);
        holder.textViewOpcio.setText(answer.getOpcio());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOpcio;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOpcio = itemView.findViewById(android.R.id.text1);
        }
    }
}