package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NpActivity extends AppCompatActivity {
    NumberPicker nPicker1;
    NumberPicker nPicker2;
    NumberPicker nPicker3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_setting);

        TextView tx_name = (TextView)findViewById(R.id.exercise_name);

        Intent name_intent = getIntent();
        String Exercise_name = name_intent.getStringExtra("exercise_name");
        tx_name.setText(Exercise_name);

        Button startButton = findViewById(R.id.exercise_start_button); //start 버튼
        startButton.setOnClickListener(v -> startActivity(new Intent(NpActivity.this, DetectorActivity.class)));

        nPicker1 = (NumberPicker) findViewById(R.id.pushup_count_np);
        nPicker1.setMinValue(20);
        nPicker1.setMaxValue(40);

        nPicker2 = (NumberPicker) findViewById(R.id.pushup_time_np);
        nPicker2.setMinValue(20);
        nPicker2.setMaxValue(50);


        nPicker3 = (NumberPicker) findViewById(R.id.pushup_set_np);
        nPicker3.setMinValue(1);
        nPicker3.setMaxValue(5);


        nPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv1 = (TextView) findViewById(R.id.pushup_text_send1);
                tv1.setText(nPicker1.getValue() + "");

                int count_picked = nPicker1.getValue();
                int time_picked = nPicker2.getValue();
                int set_picked = nPicker3.getValue();
                String count = String.valueOf(count_picked);
                String set = String.valueOf(set_picked);
                String time = String.valueOf(time_picked);

                Button startButton = findViewById(R.id.exercise_start_button); //루틴 시작 버튼
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent exercise_intent = new Intent(NpActivity.this, DetectorActivity.class);
                        exercise_intent.putExtra("exercise_name", Exercise_name);
                        exercise_intent.putExtra("count_num", count);
                        exercise_intent.putExtra("set_num", set);
                        exercise_intent.putExtra("time_num", time);
                        startActivity(exercise_intent);
                    }
                });
            }
        });
        nPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv2 = (TextView) findViewById(R.id.pushup_text_send2);
                tv2.setText(nPicker2.getValue() + "");

                int count_picked = nPicker1.getValue();
                int time_picked = nPicker2.getValue();
                int set_picked = nPicker3.getValue();
                String count = String.valueOf(count_picked);
                String set = String.valueOf(set_picked);
                String time = String.valueOf(time_picked);

                Button startButton = findViewById(R.id.exercise_start_button); //루틴 시작 버튼
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent exercise_intent = new Intent(NpActivity.this, DetectorActivity.class);
                        exercise_intent.putExtra("exercise_name", Exercise_name);
                        exercise_intent.putExtra("count_num", count);
                        exercise_intent.putExtra("set_num", set);
                        exercise_intent.putExtra("time_num", time);
                        startActivity(exercise_intent);
                    }
                });
            }
        });
        nPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv3 = (TextView) findViewById(R.id.pushup_text_send3);
                tv3.setText(nPicker3.getValue() + "");

                int count_picked = nPicker1.getValue();
                int time_picked = nPicker2.getValue();
                int set_picked = nPicker3.getValue();
                String count = String.valueOf(count_picked);
                String set = String.valueOf(set_picked);
                String time = String.valueOf(time_picked);

                Button startButton = findViewById(R.id.exercise_start_button); //루틴 시작 버튼
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent exercise_intent = new Intent(NpActivity.this, DetectorActivity.class);
                        exercise_intent.putExtra("exercise_name", Exercise_name);
                        exercise_intent.putExtra("count_num", count);
                        exercise_intent.putExtra("set_num", set);
                        exercise_intent.putExtra("time_num", time);
                        startActivity(exercise_intent);
                    }
                });
            }
        });


    }
}

