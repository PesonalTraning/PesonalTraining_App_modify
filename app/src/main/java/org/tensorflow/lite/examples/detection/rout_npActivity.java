package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class rout_npActivity extends AppCompatActivity{
    NumberPicker nPicker1;

    private TextView tx_name;
    private TextView tx_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_number_setting);

        tx_name = (TextView) findViewById(R.id.routine_exercise_name);

        Intent name_intent = getIntent();
        String Exercise_name = name_intent.getStringExtra("exercise_name");
        tx_name.setText(Exercise_name);

        nPicker1 = (NumberPicker) findViewById(R.id.routine_count_np);
        nPicker1.setMinValue(20);
        nPicker1.setMaxValue(40);

        nPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv1 = (TextView) findViewById(R.id.routine_text_send1);
                tv1.setText(nPicker1.getValue() + "");

                Intent name_intent = getIntent();
                String Exercise_name = name_intent.getStringExtra("exercise_name");
                tx_name.setText(Exercise_name);

                int picked = nPicker1.getValue();
                String ex_num = String.valueOf(picked);
                Intent picked_intent = new Intent(rout_npActivity.this, RoutineActivity.class);
                String situp = "윗몸일으키기";
                String pushup = "팔굽혀펴기";
                String squat = "스쿼트";
                String pullup = "턱걸이";
                String ex_name = tx_name.getText().toString();
                if(ex_name.equals(situp)){picked_intent.putExtra("situp_num", ex_num);}
                else if(ex_name.equals(pushup)){picked_intent.putExtra("pushup_num", ex_num);}
                else if(ex_name.equals(squat)){picked_intent.putExtra("squat_num", ex_num);}
                else if(ex_name.equals(pullup)){picked_intent.putExtra("pullup_num", ex_num);}

                Button plusButton = findViewById(R.id.routine_plus_button); //my화면 버튼
                plusButton.setOnClickListener(v -> startActivity(picked_intent));
            }
        });


    }

}
