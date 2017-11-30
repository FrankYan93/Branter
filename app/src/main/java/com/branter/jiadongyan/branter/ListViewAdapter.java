package com.branter.jiadongyan.branter;

import android.app.Activity;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11/28/17.
 */

public class ListViewAdapter  extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Activity context;
    private List<GridTest> list;
    private List<GridTest> filterlist;
    private FinalBitmap finalBitmap;
    private GridViewAdapter gridViewAdapter;
    private int wh;
    private FriendFilter friendFilter;
    public ListViewAdapter(Activity context, List<GridTest> list){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.wh=(SysUtils.getScreenWidth(context)- SysUtils.Dp2Px(context, 99))/3;
        this.list = list;
        this.filterlist = list;
        this.finalBitmap = FinalBitmap.create(context);
        this.finalBitmap.configLoadfailImage(R.drawable.ic_menu_camera);
    }

    @Override
    public Filter getFilter(){
        if (friendFilter == null){
            friendFilter = new FriendFilter();
        }
        return friendFilter;
    }


    public List<GridTest> getlist(){
        return  filterlist;
    }
    @Override
    public int getCount() {

        return filterlist == null ? 0 : filterlist.size();
    }

    @Override
    public Object getItem(int position) {

        return filterlist == null ? null : filterlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterlist == null ? null : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (filterlist.size()==0){
            return null;
        }
        final ViewHolder holder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_listview,null);
            holder = new ViewHolder();
            holder.headphoto = (ImageView) convertView.findViewById(R.id.info_iv_head);
            holder.disName = (TextView) convertView.findViewById(R.id.info_tv_name);
            holder.time = (TextView) convertView.findViewById(R.id.info_tv_time);
            holder.content = (TextView) convertView.findViewById(R.id.info_tv_content);
            holder.rl4=(RelativeLayout) convertView.findViewById(R.id.rl4);
            holder.gv_images = (MyGridView) convertView.findViewById(R.id.gv_images);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        final GridTest gridTest = filterlist.get(position);
        String name = null,time = null,content = null,headpath = null,contentimage = null;
        if(gridTest!=null){
            name = gridTest.getEventTitle();
            time = gridTest.getTime();
            content = gridTest.getContent();
            headpath = gridTest.getHeadphoto();
            contentimage =gridTest.getImage();
        }

        if (name!=null&&!name.equals("")) {
            holder.disName.setText(name);
        }

        if (contentimage!=null&&!contentimage.equals("")) {
            holder.rl4.setVisibility(View.VISIBLE);
            initInfoImages(holder.gv_images,contentimage);
        } else {
            holder.rl4.setVisibility(View.GONE);
        }

        if (time!=null&&!time.equals("")) {
            holder.time.setText(time);
        }

        if (content!=null&&!content.equals("")) {
            holder.content.setText(content);
            Linkify.addLinks(holder.content, Linkify.WEB_URLS);
        }

        if (headpath!=null&&!headpath.equals("")) {
            finalBitmap.display(holder.headphoto,headpath);
        } else {
            holder.headphoto.setImageResource(R.drawable.ic_menu_camera);
        }
        holder.headphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context, "Click on the head photo", Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
    static class ViewHolder {
        ImageView headphoto;
        TextView disName;
        TextView time;
        TextView content;
        MyGridView gv_images;
        RelativeLayout rl4;
    }
    public void initInfoImages(MyGridView gv_images,final String imgspath){
        if(imgspath!=null&&!imgspath.equals("")){
            String[] imgs=imgspath.split("#");
            ArrayList<String> list=new ArrayList<String>();
            for(int i=0;i<imgs.length;i++){
                list.add(imgs[i]);
            }
            int w=0;
            switch (imgs.length) {
                case 1:
                    w=wh;
                    gv_images.setNumColumns(1);
                    break;
                case 2:
                case 4:
                    w=2*wh+SysUtils.Dp2Px(context, 2);
                    gv_images.setNumColumns(2);
                    break;
                case 3:
                case 5:
                case 6:
                    w=wh*3+SysUtils.Dp2Px(context, 2)*2;
                    gv_images.setNumColumns(3);
                    break;
            }
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.WRAP_CONTENT);
            gv_images.setLayoutParams(lp);

            gridViewAdapter=new GridViewAdapter(context, list);
            gv_images.setAdapter(gridViewAdapter);
            gv_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                    Toast.makeText(context, "clicked on"+(arg2+1)+"picture", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<GridTest> tempList = new ArrayList<GridTest>();

                // search content in friend list
                for (GridTest user : list) {
                    if (user.getEventTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = list.size();
                filterResults.values = list;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterlist = (ArrayList<GridTest>) results.values;
            notifyDataSetChanged();
        }
    }

}
