package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private int startYear, startMonth, startDate, endYear, endMonth, endDate;
    private long diffSec, diffDays;
    private int start = 0, end = 0, newStart = 0, newEnd = 0;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View calendarActivity = inflater.inflate(
                R.layout.activity_calendar, container, false
        );
        ArrayList<CalendarListViewItem> items = new ArrayList<>();

        List<EventDay> events = new ArrayList<>();
        com.applandeo.materialcalendarview.CalendarView calendarView
                = (com.applandeo.materialcalendarview.CalendarView) calendarActivity.findViewById(R.id.calendarView);

        ListView calendar;
        CalendarListViewAdapter calendarAdapter;

        calendarAdapter = new CalendarListViewAdapter(getActivity(), R.layout.activity_calendar_listviewitem, items);
        calendar = (ListView) calendarActivity.findViewById(R.id.listView);
        calendar.setAdapter(calendarAdapter);

        calendarAdapter.addItem(0, "뮤지컬 엘리자벳","블루스퀘어 인터파크홀","2018.11.17~2019.02.10");
        calendarAdapter.addItem(1, "테양의서커스 쿠자","잠실종합운동장 내 빅탑","2018.11.03~2019.01.06");
        calendarAdapter.addItem(2, "뮤지컬 젠틀맨스 가이드:사랑과 살인편","홍익대 대학로 아트센터 대극장","2018.11.09~2019.01.27");
        calendarAdapter.addItem(3, "뮤지컬 영웅 10주년 기념공연","세종문화회관 대극장","2019.03.09~2019.04.21");
        calendarAdapter.addItem(4, "뮤지컬 라이온 킹 인터네셔널 투어","예술의전당 오페라극장","2019.01.09~2019.03.28");

        dateToString("2018.11.17~2019.02.10");
        count++;

        Calendar startCal = new GregorianCalendar(startYear, startMonth-1, startDate);
        Calendar currentCal = Calendar.getInstance(Locale.KOREA);
        Calendar endCal = new GregorianCalendar(endYear, endMonth-1, endDate);

        diffSec = currentCal.getTimeInMillis() - startCal.getTimeInMillis();
        diffDays = diffSec / (24 * 60 * 60 * 1000);
        start = -(int)diffDays;

        diffSec = (endCal.getTimeInMillis() - currentCal.getTimeInMillis());
        diffDays = diffSec / (24 * 60 * 60 * 1000);
        end = (int)diffDays;

        // 하나만 추가했을 경우
        for(int i = start; i <= end; i++) {
            Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
            calendarEvent.add(Calendar.DAY_OF_MONTH, i);
            events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 0)));
        }
        calendarView.setEvents(events);



//        dateToString("2018.11.03~2019.01.06");
//        Calendar newStartCal = new GregorianCalendar(startYear, startMonth-1, startDate);
//        Calendar newEndCal = new GregorianCalendar(endYear, endMonth-1, endDate);
//        count++;
//
//        if(count%5 == 2) {
//            diffSec = startCal.getTimeInMillis() - newStartCal.getTimeInMillis();
//            diffDays = diffSec / (24 * 60 * 60 * 1000);
//            newStart = -(int)diffDays;
//
//            diffSec = (newEndCal.getTimeInMillis() - endCal.getTimeInMillis());
//            diffDays = diffSec / (24 * 60 * 60 * 1000);
//            newEnd = (int)diffDays;
//
//            if(newStart > 0) {
//                for(int i = newStart; i < start; i++) {
//                    Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                    calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                    events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 1)));
//                }
//                if(newEnd > 0) {
//                    for(int i = newEnd+1; i <= end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 0)));
//                    }
//                } else if(newEnd == 0) {
//                    for(int i = start; i < end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawTwoEvents(getActivity(), 0, 1)));
//                    }
//                } else if(newEnd < 0) {
//                    for(int i = end+1; i <= newEnd; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 1)));
//                    }
//                }
//            } else if(newStart == 0) {
//                for(int i = start; i <= newEnd; i++) {
//                    Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                    calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                    events.add(new EventDay(calendarEvent, drawTwoEvents(getActivity(), 0, 1)));
//                }
//                if(newEnd > 0) {
//                    for(int i = newEnd+1; i <= end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 0)));
//                    }
//                } else if(newEnd == 0) {
//                    for(int i = start; i < end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawTwoEvents(getActivity(), 0, 1)));
//                    }
//                } else if(newEnd < 0) {
//                    for(int i = end+1; i <= newEnd; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 1)));
//                    }
//                }
//            } else if(newStart < 0) {
//                for(int i = start; i <= newStart-1; i++) {
//                    Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                    calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                    events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 0)));
//                }
//                if(newEnd > 0) {
//                    for(int i = newEnd+1; i <= end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 0)));
//                    }
//                } else if(newEnd == 0) {
//                    for(int i = start; i < end; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawTwoEvents(getActivity(), 0, 1)));
//                    }
//                } else if(newEnd < 0) {
//                    for(int i = end+1; i <= newEnd; i++) {
//                        Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                        calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//                        events.add(new EventDay(calendarEvent, drawSingleEvent(getActivity(), 1)));
//                    }
//                }
//            }
//            // 하나만 추가했을 경우
//            for(int i = start; i <= end; i++) {
//                events.clear();
//                Calendar calendarEvent = Calendar.getInstance(Locale.KOREA);
//                calendarEvent.add(Calendar.DAY_OF_MONTH, i);
//
//                if(count == 2)
//                    events.add(new EventDay(calendarEvent, drawTwoEvents(this, 0, 1)));
//                else if(count == 1)
//                    events.add(new EventDay(calendarEvent, drawSingleEvent(this, 1)));
//            }
//            calendarView.setEvents(events);
//        }
        return calendarActivity;
    }

    private void dateToString(String date) {
        startYear = Integer.parseInt(date.substring(0, 4));
        startMonth = Integer.parseInt(date.substring(5, 7));
        startDate = Integer.parseInt(date.substring(8, 10));
        endYear = Integer.parseInt(date.substring(11, 15));
        endMonth = Integer.parseInt(date.substring(16, 18));
        endDate = Integer.parseInt(date.substring(19, 21));
    }

    public Drawable drawTwoEvents(Context context, int color1, int color2){
        Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.circle_icon);
        Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.circle_icon_2);

        if(color1 == 0) drawable1.setTint(Color.GREEN);
        else if(color1 == 1) drawable1.setTint(Color.CYAN);
        else if(color1 == 2) drawable1.setTint(Color.MAGENTA);
        else if(color1 == 3) drawable1.setTint(Color.BLUE);
        else if(color1 == 4) drawable1.setTint(Color.RED);

        if(color2 == 0) drawable2.setTint(Color.GREEN);
        else if(color2 == 1) drawable2.setTint(Color.CYAN);
        else if(color2 == 2) drawable2.setTint(Color.MAGENTA);
        else if(color2 == 3) drawable2.setTint(Color.BLUE);
        else if(color2 == 4) drawable2.setTint(Color.RED);

        LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {drawable1, drawable2});
        finalDrawable.setLayerInset(0, 0, 0, 90, 0);
        finalDrawable.setLayerInset(1, 90, 0, 0, 0);

        //Add padding to too large icon
        return new InsetDrawable(finalDrawable, 0, 0, 0, 0);
    }

    public Drawable drawSingleEvent(Context context, int color){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_icon);

        if(color == 0) drawable.setTint(Color.GREEN);
        else if(color == 1) drawable.setTint(Color.CYAN);
        else if(color == 2) drawable.setTint(Color.MAGENTA);
        else if(color == 3) drawable.setTint(Color.BLUE);
        else if(color == 4) drawable.setTint(Color.RED);

        //Add padding to too large icon
        return new InsetDrawable(drawable, 50, 0, 50, 0);
    }

    public Drawable drawMultipleEvents(Context context, int color1, int color2){
        Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.circle_icon);
        Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.circle_icon_2);
        Drawable plus = ContextCompat.getDrawable(context, R.drawable.plus_icon);

        if(color1 == 0) drawable1.setTint(Color.GREEN);
        else if(color1 == 1) drawable1.setTint(Color.CYAN);
        else if(color1 == 2) drawable1.setTint(Color.MAGENTA);
        else if(color1 == 3) drawable1.setTint(Color.BLUE);
        else if(color1 == 4) drawable1.setTint(Color.RED);

        if(color2 == 0) drawable2.setTint(Color.GREEN);
        else if(color2 == 1) drawable2.setTint(Color.CYAN);
        else if(color2 == 2) drawable2.setTint(Color.MAGENTA);
        else if(color2 == 3) drawable2.setTint(Color.BLUE);
        else if(color2 == 4) drawable2.setTint(Color.RED);

        plus.setTint(Color.GRAY);

        LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {drawable1, drawable2, plus});
        finalDrawable.setLayerInset(0, 0, 0, 180, 0);
        finalDrawable.setLayerInset(1, 90, 0, 90, 0);
        finalDrawable.setLayerInset(2, 180, 0, 0, 0);

        //Add padding to too large icon
        return new InsetDrawable(finalDrawable, 0, 0, 0, 0);
    }
}
