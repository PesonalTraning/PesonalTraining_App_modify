package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Setting_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button exerciseButton = findViewById(R.id.exercise_Button);
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(Setting_Activity.this, Exercise_Activity.class)));

        Button routineButton = findViewById(R.id.routine_Button);
        routineButton.setOnClickListener(v -> startActivity(new Intent(Setting_Activity.this, Routine_Activity.class)));

        Button myButton = findViewById(R.id.my_Button);
        myButton.setOnClickListener(v -> startActivity(new Intent(Setting_Activity.this, MainActivity.class)));
    }
}
