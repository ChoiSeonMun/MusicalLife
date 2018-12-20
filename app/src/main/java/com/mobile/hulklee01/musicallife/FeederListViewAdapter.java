package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class FeederListViewAdapter extends ArrayAdapter {

    private int mLayoutID = 0;

    private ArrayList<MusicalInfo> mList;

    public FeederListViewAdapter(Context context, int resource, ArrayList<MusicalInfo> list) {
        super(context, resource, list);
        this.mLayoutID = resource;
        this.mList = list;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_feeder, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView musicalImageView = (ImageView)convertView.findViewById(R.id.musical_image);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.muscial_title);
        TextView placeTextView = (TextView) convertView.findViewById(R.id.muscial_place);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.musical_date);
        Button addButton = (Button) convertView.findViewById(R.id.button_add);

        // 아이템 내 각 위젯에 데이터 반영
        MusicalInfo item = mList.get(position);
        Glide.with(getContext()).load(item.Image).into(musicalImageView);
        titleTextView.setText(item.Title);
        placeTextView.setText(item.Location);
        dateTextView.setText(item.Duration);
        addButton.setTag(position);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
}
