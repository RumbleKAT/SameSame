package com.example.songmyeongjin.samesame;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by songmyeongjin on 2017. 4. 27..
 */

public class CustomAdapter extends PagerAdapter {

    LayoutInflater inflater;
    private int count;
    WebImage Image;

    public CustomAdapter(LayoutInflater inflater){
        this.inflater = inflater;
        count = 0;
    }

    public Object instantiateItem(ViewGroup container, int position){
        View view = null;
        view = inflater.inflate(R.layout.childview,null);
        ImageView img = (ImageView)view.findViewById(R.id.childimage);
        Image = new WebImage();
        container.addView(view);

        return view;
    }

    public void destoryItem(ViewGroup container, int position ,Object object){
        container.removeView((View)object);
    }


    //이미지의 개수에 따라 정해짐 가변적으로 해당 이미지를 바꿔야함

    @Override
    public int getCount() {
        this.count = 1;
        return this.count;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
