package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Camera_Ex_Activity extends AppCompatActivity {

    private TextView tv_exercise_name;
    private TextView tv_time_num;
    private TextView tv_set_num;
    private TextView tv_count_num;

    private TextView tv_situp_num;
    private TextView tv_pushup_num;
    private TextView tv_squat_num;
    private TextView tv_pullup_num;

    String exercise_name = "00";
    String time_num = "00";
    String set_num = "00";
    String count_num = "00";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfe_od_activity_camera);

        tv_exercise_name = (TextView) findViewById(R.id.exercise_name);
        tv_time_num = (TextView) findViewById(R.id.time);
        tv_set_num = (TextView) findViewById(R.id.set);
        tv_count_num = (TextView) findViewById(R.id.count);

        Intent name_intent = getIntent();
        String exercise_name = name_intent.getStringExtra("exercise_name");
        String count_num = name_intent.getStringExtra("situp_count");
        String set_num = name_intent.getStringExtra("set_num");
        String time_num = name_intent.getStringExtra("time_num");

        tv_exercise_name.setText(exercise_name);
        tv_time_num.setText(time_num);
        tv_set_num.setText(set_num);
        tv_count_num.setText(count_num);
    }
}
