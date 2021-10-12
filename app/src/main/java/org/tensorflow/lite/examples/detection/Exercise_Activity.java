package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Exercise_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Button routineButton = findViewById(R.id.routine_Button);
        routineButton.setOnClickListener(v -> startActivity(new Intent(Exercise_Activity.this, Routine_Activity.class)));

        Button myButton = findViewById(R.id.my_Button);
        myButton.setOnClickListener(v -> startActivity(new Intent(Exercise_Activity.this, MainActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button);
        settingButton.setOnClickListener(v -> startActivity(new Intent(Exercise_Activity.this, Setting_Activity.class)));
    }
}
