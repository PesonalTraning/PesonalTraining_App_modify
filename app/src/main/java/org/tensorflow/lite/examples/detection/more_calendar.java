package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

public class more_calendar extends AppCompatActivity {
    int y=2021,m=7,d=15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_calendar);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(V -> startActivity(new Intent(more_calendar.this,MainActivity.class)));

        MaterialCalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setSelectedDate(CalendarDay.today());

        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(y,m,d))));
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2021,7,2))));
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2021,7,10))));
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2021,7,16))));
        calendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(2021,7,29))));

    }

}
