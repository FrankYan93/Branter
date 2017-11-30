package com.branter.jiadongyan.branter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import net.tsz.afinal.FinalBitmap;

/**
 * Created by Jerry on 11/28/17.
 */

public class GridViewAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> list;
    public Bitmap bitmaps[];
    private FinalBitmap finaImageLoader;
    private int wh;
    public GridViewAdapter(Activity context,ArrayList<String> list){
        this.context = context;
        this.list = list;
        this.wh=(SysUtils.getScreenWidth(context)-SysUtils.Dp2Px(context, 99))/3;
        this.finaImageLoader = FinalBitmap.create(context);
        this.finaImageLoader.configLoadfailImage(R.drawable.ic_menu_gallery);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview,null);
            holder = new Holder();
            holder.imageView = (ImageView)view.findViewById(R.id.imagevView);
            view.setTag(holder);

        }
        else{
            holder = (Holder)view.getTag();
        }

        finaImageLoader.display(holder.imageView, list.get(position));
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(wh,wh);

        view.setLayoutParams(param);
        return view;

    }
    class Holder{
        ImageView imageView;
    }

}
