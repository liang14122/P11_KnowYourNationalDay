package com.example.a16004118.p11_knowyournationalday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizDialogAdapter extends BaseAdapter {

     ArrayList<Quiz> quizList;

    private LayoutInflater layoutInflater;

    public QuizDialogAdapter(Context context) {
        quizList = new ArrayList<>();
        quizList.add(new Quiz("Singapore NationalDay did on 8 Sept"));
        quizList.add(new Quiz("Singapore is 53 yrs old"));
        quizList.add(new Quiz("National Day theme is 'We Are Singapore'"));
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Quiz getItem(int position) {
        return quizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        final QuizHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.quiz_row, null);
            holder = new QuizHolder();
            holder.tvQuestion = convertView.findViewById(R.id.tvQuestion);
            holder.rg =  convertView.findViewById(R.id.rg);
            convertView.setTag(holder);
        } else {
            holder = (QuizHolder) convertView.getTag();
        }

        final Quiz currentQuiz = quizList.get(position);

        holder.tvQuestion.setText(currentQuiz.getQuestion());

        holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedButtonId = holder.rg.getCheckedRadioButtonId();

                if (selectedButtonId == R.id.rbYes){
                    currentQuiz.setAnswer(true);
                }else{
                    currentQuiz.setAnswer(false);
                }
            }
        });
        return null;
    }

    static class QuizHolder {
        TextView tvQuestion;
        RadioGroup rg;
    }
}
