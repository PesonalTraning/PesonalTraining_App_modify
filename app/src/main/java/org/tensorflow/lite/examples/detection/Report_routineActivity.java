package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Report_routineActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_routine);

        Button okButton = findViewById(R.id.routine_report_ok); //확인 버튼
        okButton.setOnClickListener(v -> startActivity(new Intent(Report_routineActivity.this,MainActivity.class)));
    }
}
