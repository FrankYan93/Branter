package com.branter.jiadongyan.branter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.*;

public class EventDetail extends AppCompatActivity {
    private List<GridTest> listgrid;
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private String imgs1;
    private  String imgs3;
    private  String imgs2;
    private String imgs4;
    private String eventId;
    private Button join;
    ArrayList<String> arrayList = new ArrayList<>();

    @SuppressWarnings("deprecation")
//    Gallery Imagegallery;
    Integer[] GalleryImagesList =
            {
                    R.drawable.one,
                    R.drawable.two,
                    R.drawable.three,
                    R.drawable.four,
                    R.drawable.five,
                    R.drawable.six,
                    R.drawable.seven
            };
    ImageView imgGalleryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);;

        Intent data = getIntent();
        Bundle extras = data.getExtras();

        eventId = extras.getString("id");
        SaveSharedPreference.setEventID(this,eventId);
        System.out.println(SaveSharedPreference.getEventID(this));
        System.out.println("debuggggggggg");

        listgrid = new ArrayList<GridTest>();
        init();
        initData();


        View header = getLayoutInflater().inflate(R.layout.header, null);
        listView.addHeaderView(header);

        imgGalleryImage = (ImageView)findViewById(R.id.imgGalleryImage);
        imgGalleryImage.setImageResource(R.drawable.one);

//        Imagegallery = (Gallery)findViewById(R.id.gallery);
//        Imagegallery.setAdapter(new ImageAdapter(this));
//        Imagegallery.setOnItemClickListener(new OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id)
//            {
//                imgGalleryImage.setImageResource(GalleryImagesList[position]);
//            }
//        });


        //listView = (ListView) findViewById(R.id.f_listview);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));

        Button post = (Button) findViewById(R.id.EventDetail_fab);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EventDetail.this, PostActivity.class);
                startActivityForResult(intent1, 1);


            }
        });

        join = (Button) findViewById(R.id.EventDetail_JoinButton);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.EventDetail_JoinButton:
                        // post join events
                        Thread one = new Thread() {
                            public void run() {
                                try {
                                    CSC client = new CSC();
                                    client.followEvent(eventId,EventDetail.this);
                                } catch(Exception v) {
                                }
                            }
                        };

                        one.start();
                        try {
                            one.join();
                        } catch (InterruptedException e) {

                        }
                        join.setText("joined");
                        break;
                    default: break;
                }
            }
        });

        listViewAdapter = new ListViewAdapter(this,listgrid);
        listView.setAdapter(listViewAdapter);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(new Intent(this, PostActivity.class), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                listgrid = new ArrayList<GridTest>();
                init();
//        initData();

                // get all posts of this event
                Thread one = new Thread() {
                    public void run() {
                        try {
                            CSC client = new CSC();
                            Log.e("Event id",eventId);
                            Post[] allPosts = client.getEventPosts(eventId);
                            Log.e("Event id",eventId);
                            Log.e("post info", Integer.toString(allPosts.length));
                            for (int i = 0; i < allPosts.length; i++) {
                                GridTest single = new GridTest();
                                Post singlePost = allPosts[i];
                                User singleUser = client.getUserInformation(singlePost.user_id);
                                single.setEventTitle(singleUser.username);
                                single.setContent(singlePost.content);
                                single.setTime("");
                                single.setHeadphoto("http://www.ayso1236.us/wp-content/uploads/2017/11/cow-cartoon-drawing-monkey-coloring-page.jpg");
                                single.setImage(FakeImg.img[new Random().nextInt(FakeImg.img.length)]);
                                single.setId(singlePost.id);
                                listgrid.add(single);
                            }
                        } catch(Exception v) {
                        }
                    }
                };

                one.start();
                try {
                    one.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listViewAdapter = new ListViewAdapter(this,listgrid);
                listView.setAdapter(listViewAdapter);
            }
        }
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("test1");
        data.add("test2");
        data.add("test3");
        data.add("test4");

        return data;
    }

    private class ImageAdapter extends BaseAdapter
    {
        Context context;
        public ImageAdapter(Context context)
        {
            this.context = context;
        }
        @Override
        public int getCount()
        {
            return GalleryImagesList.length;
        }

        @Override
        public Object getItem(int position)
        {
            return GalleryImagesList[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView = new ImageView(this.context);
            imageView.setImageResource(GalleryImagesList[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(150, 200));
            imageView.setScaleType(ScaleType.FIT_XY);

            return imageView;
        }

    }

    private void  init(){
        listView = (ListView)findViewById(R.id.f_listview);

    }

    private void initData(){
        FakeImg.create();
        switch (eventId) {
            case "Football":
                GridTest[] fake = FakeImg.footballposts;
                for (int i = 0; i < fake.length; i++)
                    listgrid.add(fake[i]);
                break;
            case "":
                break;
            default:
                // get all posts of this event
                Thread one = new Thread() {
                    public void run() {
                        try {
                            CSC client = new CSC();
                            Log.e("Event id",eventId);
                            Post[] allPosts = client.getEventPosts(eventId);
                            Log.e("Event id",eventId);
                            Log.e("post info", Integer.toString(allPosts.length));
                            for (int i = 0; i < allPosts.length; i++) {
                                GridTest single = new GridTest();
                                Post singlePost = allPosts[i];
                                User singleUser = client.getUserInformation(singlePost.user_id);
                                single.setEventTitle(singleUser.username);
                                single.setContent(singlePost.content);
                                single.setTime("");
                                single.setHeadphoto("http://www.ayso1236.us/wp-content/uploads/2017/11/cow-cartoon-drawing-monkey-coloring-page.jpg");
                                single.setImage(FakeImg.img[new Random().nextInt(FakeImg.img.length)]);
                                single.setId(singlePost.id);
                                listgrid.add(single);
                            }
                        } catch(Exception v) {
                        }
                    }
                };

                one.start();
                try {
                    one.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

    }
}
