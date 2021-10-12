package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class more_exercise  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_exercise);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(V -> startActivity(new Intent(more_exercise.this,MainActivity.class)));
    }
}
