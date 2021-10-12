package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Routine_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Button exerciseButton = findViewById(R.id.exercise_Button);
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(Routine_Activity.this, Exercise_Activity.class)));

        Button myButton = findViewById(R.id.my_Button);
        myButton.setOnClickListener(v -> startActivity(new Intent(Routine_Activity.this, MainActivity.class)));

        Button settingButton = findViewById(R.id.setting_Button);
        settingButton.setOnClickListener(v -> startActivity(new Intent(Routine_Activity.this, Setting_Activity.class)));
    }
}
