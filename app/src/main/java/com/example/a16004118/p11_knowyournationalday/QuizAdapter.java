package com.example.a16004118.p11_knowyournationalday;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Quiz> quizList;

    public QuizAdapter(Context context,
                           int resource,
                           ArrayList<Quiz> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        quizList = objects;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        final Quiz currentQuiz = quizList.get(position);

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) parent_context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(layout_id, parent, false);
        } else {
            rowView = convertView;
        }

        TextView tvQuestion = rowView.findViewById(R.id.tvQuestion);
        final RadioGroup rg = rowView.findViewById(R.id.rg);

        tvQuestion.setText(currentQuiz.getQuestion());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedButtonId = rg.getCheckedRadioButtonId();

                if (selectedButtonId == R.id.rbYes){
                    currentQuiz.setAnswer(true);
                }else{
                    currentQuiz.setAnswer(false);
                }
            }
        });

        return rowView;
    }

}
