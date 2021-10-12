package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button myButton = findViewById(R.id.my_Button); //my화면 버튼
        myButton.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, MainActivity.class)));

        Button exerciseButton = findViewById(R.id.exercise_Button); //운동화면 버튼 (아마 개별운동이 될 것)
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, ExerciseActivity.class)));

        Button routineButton = findViewById(R.id.routine_Button); //루틴 버튼 선언
        routineButton.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, RoutineActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button); //설정창 버튼 선언?
        settingButton.setOnClickListener(v -> startActivity(new Intent(SettingActivity.this, SettingActivity.class)));
    }
}
