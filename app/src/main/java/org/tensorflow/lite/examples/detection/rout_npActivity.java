package org.tensorflow.lite.examples.detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

                Button plusButton = findViewById(R.id.routine_plus_button);
                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ex_num_intent = new Intent();
                        ex_num_intent.putExtra("ex_num", ex_num);
                        setResult(RESULT_OK, ex_num_intent);
                        finish();
                    }
                });
            }
        });


    }

}
