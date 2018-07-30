package com.example.a16004118.p11_knowyournationalday;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter<? extends String> arrayAdapter;
    private ArrayList<String> al;
    private SharedPreferences prefs;
    private ArrayList<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);

        lv = findViewById(R.id.lv);
        al = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(arrayAdapter);

        al.add("Singapore National Day is on 9 Aug");
        al.add("Singapore is 53 years old");
        al.add("Theme is 'We Are Singapore'");
        arrayAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean status = prefs.getBoolean("status", false);

        if (!status) {

            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            @SuppressLint("InflateParams") ConstraintLayout passPhrase =
                    (ConstraintLayout) inflater.inflate(R.layout.login, null);
            final EditText editTextAccessCode = passPhrase.findViewById(R.id.editTextAccessCode);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please Login")
                    .setView(passPhrase)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String code = editTextAccessCode.getText().toString().trim();

                            if (code.equals("738964")) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("status", true);
                                editor.apply();
                            } else {
                                Snackbar.make((View) dialog, "Wrong Access Code", Snackbar.LENGTH_SHORT).show();
                            }


                        }
                    }).setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemQuit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Quit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                @SuppressLint("CommitPrefEdits")
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.apply();
                                finish();
                            }
                        })
                        .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.itemSendToFriends:
                String[] list = new String[]{"Email", "SMS"};
                AlertDialog.Builder builderSend = new AlertDialog.Builder(this);
                builderSend.setTitle("Select the way to enrich your friend")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    //send email
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("message/rfc822");
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "National Day");
                                    intent.putExtra(Intent.EXTRA_TEXT, "Know Your National Day");
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"632944695@qq.com"});

//                                    startActivity(Intent.createChooser(intent, "Send Email"));
                                    startActivityForResult(Intent.createChooser(intent, "Send Email"), 123);
                                } else {
                                    //send sms

                                    int phoneNumber = 85351749;
                                    String message = "Know Your National Day";

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                                    intent.putExtra("sms_body", message);
                                    startActivityForResult(intent, 456);

                                }

                            }
                        });

                AlertDialog alertDialogSend = builderSend.create();
                alertDialogSend.show();
                break;

            case R.id.itemQuiz:

                LayoutInflater inflaterQuiz =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflaterQuiz != null;
                @SuppressLint("InflateParams") ConstraintLayout quiz =
                        (ConstraintLayout) inflaterQuiz.inflate(R.layout.quiz, null);

                ListView lvQuiz = quiz.findViewById(R.id.lvQuiz);

                quizList = new ArrayList<>();
                quizList.add(new Quiz("Singapore NationalDay did on 8 Sept"));
                quizList.add(new Quiz("Singapore is 53 yrs old"));
                quizList.add(new Quiz("National Day theme is 'We Are Singapore'"));

                QuizAdapter quizAdapter = new QuizAdapter(MainActivity.this, R.layout.quiz_row, quizList);

                lvQuiz.setAdapter(quizAdapter);

                AlertDialog.Builder builderQuiz = new AlertDialog.Builder(this);
                builderQuiz.setTitle("Test Yourself!")
                        .setView(quiz)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int score = 0;

                                for (int i = 0; i < quizList.size(); i++) {
                                    Quiz currentQuiz = quizList.get(i);
                                    if (i == 0 && !currentQuiz.isAnswer()) {
                                        score++;
                                    } else if (i != 0 && currentQuiz.isAnswer()) {
                                        score++;
                                    }
                                }
                                Toast.makeText(MainActivity.this, "Your Score is " + score, Toast.LENGTH_SHORT).show();
                                quizList.clear();
                            }
                        })
                        .setNegativeButton("Don't Know lah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                quizList.clear();

                            }
                        });

                AlertDialog alertDialogQuiz = builderQuiz.create();
                alertDialogQuiz.show();


                break;
            default:
                System.out.println("no match");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
        }

    }
}
