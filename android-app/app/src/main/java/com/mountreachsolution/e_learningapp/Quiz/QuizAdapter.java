package com.mountreachsolution.e_learningapp.Quiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends BaseAdapter {

    List<PojoGetQuiz> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static ArrayList<String> selectedAnswers;
    ArrayList<String> question = new ArrayList<>();

    public QuizAdapter(List<PojoGetQuiz> list, AppCompatActivity activity, ArrayList<String> question) {
        this.list = list;
        this.activity = activity;
        this.question = question;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return question.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.lv_quiz, null);

            holder.lv_tv_get_quiz_question = (TextView) v.findViewById(R.id.lv_tv_get_quiz_question);
            holder.lv_rg_quiz = (RadioGroup) v.findViewById(R.id.lv_rg_quiz);
            holder.lv_rb_quiz_option1 = (RadioButton) v.findViewById(R.id.lv_rb_quiz_option1);
            holder.lv_rb_quiz_option2 = (RadioButton) v.findViewById(R.id.lv_rb_quiz_option2);
            holder.lv_rb_quiz_option3 = (RadioButton) v.findViewById(R.id.lv_rb_quiz_option3);
            holder.lv_rb_quiz_option4 = (RadioButton) v.findViewById(R.id.lv_rb_quiz_option4);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final PojoGetQuiz obj = list.get(position);
        selectedAnswers = new ArrayList<>();
        holder.lv_tv_get_quiz_question.setText(obj.getQuestion());
        holder.lv_rb_quiz_option1.setText(obj.getOption1());
        holder.lv_rb_quiz_option2.setText(obj.getOption2());
        holder.lv_rb_quiz_option3.setText(obj.getOption3());
        holder.lv_rb_quiz_option4.setText(obj.getOption4());

        holder.lv_rb_quiz_option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(position, "yes");
            }
        });
// perform setOnCheckedChangeListener event on no button
        holder.lv_rb_quiz_option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set No values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(position, "no");
            }
        });

        holder.lv_rb_quiz_option3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(position, "x");
            }
        });
// perform setOnCheckedChangeListener event on no button
        holder.lv_rb_quiz_option4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set No values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(position, "Z");
            }
        });

        // on below line we are adding check change listener for our radio group.
//        holder.lv_rg_quiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                // on below line we are getting radio button from our group.
//                holder.rb_gender =(RadioButton) activity.findViewById(checkedId);
//
//            }
//        });
//
//        is_option_select_from_radiobutton= holder.lv_rg_quiz.getCheckedRadioButtonId();
//        holder.lv_rb_quiz_option1 = (RadioButton) v.findViewById(is_option_select_from_radiobutton);

        return v;
    }

    class ViewHolder
    {
        TextView lv_tv_get_quiz_question;
        RadioGroup lv_rg_quiz;
        RadioButton lv_rb_quiz_option1,lv_rb_quiz_option2,lv_rb_quiz_option3,lv_rb_quiz_option4;
        RadioButton rb_gender;
    }
}
