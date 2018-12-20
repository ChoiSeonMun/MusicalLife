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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class FeederListViewAdapter extends ArrayAdapter {

    int resourceId;

    private ArrayList<FeederListViewItem> feederList = new ArrayList<FeederListViewItem>();

    public FeederListViewAdapter(Context context, int resource, ArrayList<FeederListViewItem> list) {
        super(context, resource, list);
        this.resourceId = resource;

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return feederList.size();
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
        ToggleButton subButton = (ToggleButton) convertView.findViewById(R.id.musical_subscribe_button);


        musicalImageView.setBackground(new ShapeDrawable(new OvalShape()));
        musicalImageView.setClipToOutline(true);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        FeederListViewItem feederListViewItem = feederList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(getContext()).load("http://ticketimage.interpark.com/PlayDictionary/DATA/PlayDic/PlayDicUpload/040001/18/09/0400011809_131089_0455.gif").into(musicalImageView);
        titleTextView.setText(feederListViewItem.getMusicalTitle());
        placeTextView.setText(feederListViewItem.getMusicalPlace());
        dateTextView.setText(feederListViewItem.getMusicalDate());
        //subButton.setTag(feederListViewItem.getMusicalTitle());

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
        return feederList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(byte[] icon, String title, String location, String date) {
        FeederListViewItem item = new FeederListViewItem();

        item.setMusicalImage(icon);
        item.setMusicalTitle(title);
        item.setMusicalPlace(location);
        item.setMusicalDate(date);

        feederList.add(item);
    }
}
