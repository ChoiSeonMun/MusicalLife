package com.mobile.hulklee01.musicallife;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    int mColorCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ArrayList<CalendarListViewItem> items = new ArrayList<>();

        List<EventDay> events = new ArrayList<>();
        com.applandeo.materialcalendarview.CalendarView calendarView
                = (com.applandeo.materialcalendarview.CalendarView) findViewById(R.id.calendarView);

        ListView calendar;
        CalendarListViewAdapter calendarAdapter;

        calendarAdapter = new CalendarListViewAdapter(getApplicationContext(), R.layout.activity_calendar_listviewitem, items);

        calendar = (ListView) findViewById(R.id.listView);
        calendar.setAdapter(calendarAdapter);

        calendarAdapter.addItem(0, "Phantom","Cheonan","2018.12.01~2018.12.31");
        calendarAdapter.addItem(1, "Phantom","Cheonan","2018.12.01~2018.12.31");
        calendarAdapter.addItem(2, "Phantom","Cheonan","2018.12.01~2018.12.31");
        calendarAdapter.addItem(3, "Phantom","Cheonan","2018.12.01~2018.12.31");
        calendarAdapter.addItem(4, "Phantom","Cheonan","2018.12.01~2018.12.31");
//        for(int i = -9; i <= 5; i++) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_MONTH, i);
//            events.add(new EventDay(calendar, get2Dots(this)));
//        }
//        for(int i = -22; i <= 5; i++) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_MONTH, i);
//            events.add(new EventDay(calendar, getDots(this)));
//        }

        // calendarView.setEvents(events);

        // 어느 날짜가 선택되었는지 메시지 띄움
        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getApplicationContext(), eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show());
    }

//    public static Drawable get2Dots(Context context){
//        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_icon2);
//
//        //Add padding to too large icon
//        return new InsetDrawable(drawable, 100, 0, 100, 0);
//    }
//
//    public static Drawable getDots(Context context){
//        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_icon);
//
//        //Add padding to too large icon
//        return new InsetDrawable(drawable, 100, 0, 100, 0);
//    }
}
