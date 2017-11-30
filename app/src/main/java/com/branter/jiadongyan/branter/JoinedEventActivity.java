package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoinedEventActivity extends AppCompatActivity {

    public List<GridTest> listgrid;
    private ListViewAdapter listViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event);

        listgrid = new ArrayList<>();

        // get all host events
        Thread one = new Thread() {
            public void run() {
                try {
                    CSC client = new CSC();
                    Event[] allEvents = client.joinedEvents(JoinedEventActivity.this);
                    for (int i = 0; i < allEvents.length; i++) {
                        GridTest single = new GridTest();
                        Event singleEvent = allEvents[i];
                        single.setEventTitle(singleEvent.title);
                        single.setContent(singleEvent.contents);
                        single.setTime("From " + singleEvent.from.split("T")[0] + " to " + singleEvent.to.split("T")[0]);
                        single.setHeadphoto("http://www.ayso1236.us/wp-content/uploads/2017/11/cow-cartoon-drawing-monkey-coloring-page.jpg");
                        single.setImage(FakeImg.img[new Random().nextInt(FakeImg.img.length)]);
                        single.setId(singleEvent.id);
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

        }
        listView = (ListView)findViewById(R.id.host_listview);
        listViewAdapter = new ListViewAdapter(this,listgrid);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(JoinedEventActivity.this,EventDetail.class);
                GridTest event = listgrid.get(position);
                intent.putExtra("title", event.getEventTitle());
                intent.putExtra("time", event.getTime());
                intent.putExtra("id",event.getId());

                startActivity(intent);
                Toast.makeText(JoinedEventActivity.this, "clicked on" + (position + 1) + "item", Toast.LENGTH_LONG).show();
            }
        });

    }

}
