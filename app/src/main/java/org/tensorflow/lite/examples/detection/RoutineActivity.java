package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RoutineActivity extends AppCompatActivity
{
    NumberPicker nPicker1;
    NumberPicker nPicker2;

    private TextView tv_situp_num;
    private TextView tv_pushup_num;
    private TextView tv_squat_num;
    private TextView tv_pullup_num;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);


        nPicker1 = (NumberPicker) findViewById(R.id.routine_set_np);
        nPicker1.setMinValue(1);
        nPicker1.setMaxValue(5);

        nPicker2 = (NumberPicker) findViewById(R.id.routine_btime_np);
        nPicker2.setMinValue(20);
        nPicker2.setMaxValue(30);

        Button myButton = findViewById(R.id.my_Button); //my화면 버튼
        myButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, MainActivity.class)));

        Button exerciseButton = findViewById(R.id.exercise_Button); //운동화면 버튼 (아마 개별운동이 될 것)
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, ExerciseActivity.class)));

        Button routineButton = findViewById(R.id.routine_Button); //루틴 버튼 선언
        routineButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, RoutineActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button); //설정창 버튼 선언?
        settingButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, Preference.class)));

//        ImageButton routineplusButton = findViewById(R.id.exercise_plus_Button);
//        routineplusButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, Routine_settingActivity.class)));

        Button startButton = findViewById(R.id.routine_start_button); //설정창 버튼 선언?
        startButton.setOnClickListener(v -> startActivity(new Intent(RoutineActivity.this, DetectorActivity.class)));


        ImageButton situpButton = findViewById(R.id.sit_up_button); //윗몸 버튼
        situpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent situp_intent = new Intent(RoutineActivity.this,rout_npActivity.class);
                situp_intent.putExtra("exercise_name","윗몸일으키기");
                startActivity(situp_intent);
            }
        });

        ImageButton pushupButton = findViewById(R.id.push_up_button); //팔굽 버튼
        pushupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pushup_intent = new Intent(RoutineActivity.this,rout_npActivity.class);
                pushup_intent.putExtra("exercise_name","팔굽혀펴기");
                startActivity(pushup_intent);
            }
        });

        ImageButton squatButton = findViewById(R.id.squat_button); //스쿼트 버튼
        squatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent squat_intent = new Intent(RoutineActivity.this,rout_npActivity.class);
                squat_intent.putExtra("exercise_name","스쿼트");
                startActivity(squat_intent);
            }
        });

        ImageButton pullupButton = findViewById(R.id.pull_up_button); //턱걸이 버튼
        pullupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pullup_intent = new Intent(RoutineActivity.this,rout_npActivity.class);
                pullup_intent.putExtra("exercise_name","턱걸이");
                startActivity(pullup_intent);
            }
        });

        Intent picked_intent = getIntent();

        String situp_num = picked_intent.getStringExtra("situp_num");
        String pushup_num = picked_intent.getStringExtra("pushup_num");
        String squat_num = picked_intent.getStringExtra("squat_num");
        String pullup_num = picked_intent.getStringExtra("pullup_num");

        tv_situp_num = (TextView) findViewById(R.id.sit_up_count);
        tv_pushup_num = (TextView) findViewById(R.id.push_up_count);
        tv_squat_num = (TextView) findViewById(R.id.squat_count);
        tv_pullup_num = (TextView) findViewById(R.id.pull_up_count);

        tv_situp_num.setText(situp_num);
        tv_pushup_num.setText(pushup_num);
        tv_squat_num.setText(squat_num);
        tv_pullup_num.setText(pullup_num);

        nPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv1 = (TextView) findViewById(R.id.routine_text_send1);
                tv1.setText(nPicker1.getValue() + "");
            }
        });
        nPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv2 = (TextView) findViewById(R.id.routine_text_send2);
                tv2.setText(nPicker2.getValue() + "");
            }
        });

    }
}
