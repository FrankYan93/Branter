package com.branter.jiadongyan.branter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    ArrayList<String> arrayList = arrayList = new ArrayList<>();

    @SuppressWarnings("deprecation")
    Gallery Imagegallery;
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
        listgrid = new ArrayList<GridTest>();
        init();
        initData();
        View header = getLayoutInflater().inflate(R.layout.header, null);
        listView.addHeaderView(header);

        imgGalleryImage = (ImageView)findViewById(R.id.imgGalleryImage);
        imgGalleryImage.setImageResource(R.drawable.one);

        Imagegallery = (Gallery)findViewById(R.id.gallery);
        Imagegallery.setAdapter(new ImageAdapter(this));
        Imagegallery.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                imgGalleryImage.setImageResource(GalleryImagesList[position]);
            }
        });


        Intent data = getIntent();
        Bundle extras = data.getExtras();
        TextView hostname = (TextView) findViewById(R.id.EventDetail_HostName);
        hostname.setText(extras.getString("title"));
        TextView eventtime = (TextView) findViewById(R.id.EventDetail_TimeName);
        eventtime.setText(extras.getString("time"));
        eventId = extras.getString("id");
        SaveSharedPreference.setEventID(this,eventId);
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
                                    client.followEvent(eventId);
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
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(new Intent(this, PostActivity.class), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

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

        //TODO:get all events data(Event class, now is using GridTest)
        imgs1="http://pic60.nipic.com/file/20150211/18733170_145247158001_2.jpg#"
                +"http://mvimg1.meitudata.com/566507ca1bcc65451.jpg";
        imgs2="http://rj1.douguo.net/upload/diet/6/6/8/666f180617cab130bef1dea9fb3f7fe8.jpg#" +
                "http://img4.duitang.com/uploads/blog/201312/01/20131201120117_F5QXY.jpeg#"+
                "http://www.sh.xinhuanet.com/133071048_13905438457501n.jpg";
        imgs3= "http://image.tianjimedia.com/uploadImages/2014/133/11/EN2I6768CHU1_1000x500.jpg#"+
                "http://pic72.nipic.com/file/20150716/6659253_104414205000_2.jpg#"+
                "http://pic36.nipic.com/20131222/10558908_214221305000_2.jpg";
        imgs4 = "http://h.hiphotos.baidu.com/zhidao/pic/item/5243fbf2b21193133f9f1e3967380cd790238d5f.jpg";
        GridTest gridTest = null;
        for(int i = 0;i<=3;i++){
            gridTest = new GridTest();
            switch (i){
                case 0:gridTest.setEventTitle("Cat discovery");
                    gridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                    gridTest.setContent("This is the cat event!......");
                    gridTest.setTime("11/25/2017 - 11/28/2017");
                    gridTest.setImage(imgs1);
                    break;
                case 1:
                    gridTest.setEventTitle("Enjoy Japanese Food");
                    gridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                    gridTest.setContent("This is the food event!.....");
                    gridTest.setTime("11/20/2017 - 11/27/2017");
                    gridTest.setImage(imgs2);
                    break;
                case 2:
                    gridTest.setEventTitle("travel to mountain");
                    gridTest.setHeadphoto("http://img5q.duitang.com/uploads/item/201404/03/20140403135406_XFS3M.jpeg");
                    gridTest.setContent("This is the travel event!.....");
                    gridTest.setTime("11/12/2017 - 11/20/2017");
                    gridTest.setImage(imgs3);
                    break;
                case 3:
                    gridTest.setEventTitle("Coding practice");
                    gridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                    gridTest.setContent("This is the programming event!.....");
                    gridTest.setTime("11/10/2017 - 11/18/2017");
                    gridTest.setImage(imgs4);
                    break;
            }
            listgrid.add(gridTest);
        }

        listViewAdapter = new ListViewAdapter(this,listgrid);
        listView.setAdapter(listViewAdapter);
    }
}

