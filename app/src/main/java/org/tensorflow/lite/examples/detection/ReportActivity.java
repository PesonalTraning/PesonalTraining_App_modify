package org.tensorflow.lite.examples.detection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ReportActivity extends Activity {

    private TextView tv_exercise_name;
    private TextView tv_time_num;
    private TextView tv_set_num;
    private TextView tv_count_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        tv_exercise_name = (TextView) findViewById(R.id.exercise_name);
        tv_time_num = (TextView) findViewById(R.id.time);
        tv_set_num = (TextView) findViewById(R.id.set);
        tv_count_num = (TextView) findViewById(R.id.count);

        Intent name_intent = getIntent();
        String exercise_name = name_intent.getStringExtra("exercise_name");
        String count_num = name_intent.getStringExtra("count_num");
        String set_num = name_intent.getStringExtra("set_num");
        String time_num = name_intent.getStringExtra("time_num");

        tv_exercise_name.setText(exercise_name);
        tv_time_num.setText(time_num);
        tv_set_num.setText(set_num);
        tv_count_num.setText(count_num);

        Button okButton = findViewById(R.id.report_ok); //확인 버튼
        okButton.setOnClickListener(v -> startActivity(new Intent(ReportActivity.this, MainActivity.class)));


    }
}
