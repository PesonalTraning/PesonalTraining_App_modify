package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Routine_settingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_exercise);

        Button myButton = findViewById(R.id.my_Button); //my화면 버튼
        myButton.setOnClickListener(v -> startActivity(new Intent(Routine_settingActivity.this, MainActivity.class)));

        Button exerciseButton = findViewById(R.id.exercise_Button); //운동화면 버튼 (아마 개별운동이 될 것)
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(Routine_settingActivity.this, ExerciseActivity.class)));

        Button routineButton = findViewById(R.id.routine_Button); //루틴 버튼
        routineButton.setOnClickListener(v -> startActivity(new Intent(Routine_settingActivity.this, RoutineActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button); //설정창 버튼
        settingButton.setOnClickListener(v -> startActivity(new Intent(Routine_settingActivity.this, Preference.class)));

        ImageButton situpButton = findViewById(R.id.situpButton); //윗몸 버튼
        situpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent situp_intent = new Intent(Routine_settingActivity.this,rout_npActivity.class);
                situp_intent.putExtra("exercise_name","윗몸일으키기");
                startActivity(situp_intent);
            }
        });

        ImageButton pushupButton = findViewById(R.id.pushupButton); //팔굽 버튼
        pushupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pushup_intent = new Intent(Routine_settingActivity.this,rout_npActivity.class);
                pushup_intent.putExtra("exercise_name","팔굽혀펴기");
                startActivity(pushup_intent);
            }
        });

        ImageButton squatButton = findViewById(R.id.squatButton); //스쿼트 버튼
        squatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent squat_intent = new Intent(Routine_settingActivity.this,rout_npActivity.class);
                squat_intent.putExtra("exercise_name","스쿼트");
                startActivity(squat_intent);
            }
        });

        ImageButton pullupButton = findViewById(R.id.pullupButton); //턱걸이 버튼
        pullupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pullup_intent = new Intent(Routine_settingActivity.this,rout_npActivity.class);
                pullup_intent.putExtra("exercise_name","턱걸이");
                startActivity(pullup_intent);
            }
        });

    }
}
