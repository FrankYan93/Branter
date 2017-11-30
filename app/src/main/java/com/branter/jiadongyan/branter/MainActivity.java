package com.branter.jiadongyan.branter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener{

    public static Hashtable<String,String> accounts = new Hashtable<>();
    public static List<GridTest> listgrid = new ArrayList<>();
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private String userID;
    private String userEmail;
    private String imgs1;
    private  String imgs3;
    private  String imgs2;
    private String imgs4;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new CSCTest().execute("");
        userID = SaveSharedPreference.getUserID(MainActivity.this);
        userEmail = SaveSharedPreference.getUserName(MainActivity.this);
        if(userID == null || userID.length() == 0)
        {
            super.onCreate(savedInstanceState);
            Intent signin = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(signin);
        }
        else {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            init();
            listgrid = new ArrayList<>();
            if (listgrid.isEmpty()) initData();


            // get all events
            Thread one = new Thread() {
                public void run() {
                    try {
                        CSC client = new CSC();
                         Event[] allEvents = client.getAllEvents();
                         for (int i = 0; i < allEvents.length; i++) {
                             GridTest single = new GridTest();
                             Event singleEvent = allEvents[i];
                             single.setEventTitle(singleEvent.title);
                             single.setContent(singleEvent.contents);
                             single.setTime("From " + singleEvent.from.split("T")[0] + " to " + singleEvent.to.split("T")[0]);
                             single.setHeadphoto("http://www.ayso1236.us/wp-content/uploads/2017/11/cow-cartoon-drawing-monkey-coloring-page.jpg");
                             single.setImage(singleEvent.imageUrl);

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

            listViewAdapter = new ListViewAdapter(this,listgrid);
            listView.setAdapter(listViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(MainActivity.this,EventDetail.class);
                    GridTest event = listgrid.get(position);
                    intent.putExtra("title", event.getEventTitle());
                    intent.putExtra("time", event.getTime());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "clicked on" + (position + 1) + "item", Toast.LENGTH_LONG).show();
                }
            });

            Log.e("listGrid length", Integer.toString(listgrid.size()));

            // FloatingActionButton
            FloatingActionButton add_event = (FloatingActionButton) findViewById(R.id.fab);
            add_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    Intent addEvent = new Intent(MainActivity.this, CreateEventActivity.class);
                    startActivity(addEvent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText){
        //Toast.makeText(super.getApplicationContext(),"TextChange!", Toast.LENGTH_LONG).show();
        listViewAdapter.getFilter().filter(newText);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        TextView username = (TextView) findViewById(R.id.usernameM);
        username.setText(userEmail);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.map) {
            startActivity(new Intent("com.branter.jiadongyan.branter.MapsViewActivity"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            startActivity(new Intent("com.branter.jiadongyan.branter.MyAccountActivity"));
        } else if (id == R.id.nav_host) {
            // TODO: start host events info activity
        } else if (id == R.id.nav_join) {
            // TODO: start joined events activity
        } else if (id == R.id.nav_post) {
            // TODO: start posts info activity
        } else if (id == R.id.nav_signout) {
            Intent intent=new Intent(this,SignInActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void  init(){
        listView = (ListView)findViewById(R.id.listview);

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
                    gridTest.setTime("From 2017-11-28 to 2017-12-02");
                    gridTest.setImage(imgs1);
                    break;
                case 1:
                    gridTest.setEventTitle("Enjoy Japanese Food");
                    gridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                    gridTest.setContent("This is the food event!.....");
                    gridTest.setTime("From 2017-11-20 to 2017-11-28");
                    gridTest.setImage(imgs2);
                    break;
                case 2:
                    gridTest.setEventTitle("travel to mountain");
                    gridTest.setHeadphoto("http://img5q.duitang.com/uploads/item/201404/03/20140403135406_XFS3M.jpeg");
                    gridTest.setContent("This is the travel event!.....");
                    gridTest.setTime("From 2017-11-12 to 2017-11-20");
                    gridTest.setImage(imgs3);
                    break;
                case 3:
                    gridTest.setEventTitle("Coding practice");
                    gridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                    gridTest.setContent("This is the programming event!.....");
                    gridTest.setTime("From 2017-11-10 to 2017-11-18");
                    gridTest.setImage(imgs4);
                    break;
            }
            listgrid.add(gridTest);
        }


    }

}

class CSCTest extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        CSC csc = new CSC();
//        System.out.println("id:~~~~~~~~~~~~~~~");
//        System.out.println(csc.createUser("xxx@xxxsss","pass"));
//        System.out.println(csc.getUserInformation("4").birthday);
//        csc.updateAccount("yo","true","11-12-2017");
//        System.out.println(csc.signIn("xxx@xxx","pass"));
//        csc.createPost("1","hello world!!!!");
//        csc.createEvent(new String[] {"title"},new String[] {"mysterious event"});

//        csc.createEvent(new String[] {"title"},new String[] {"mysterious event1"});

        System.out.println(csc.getEventsByUserId("1"));

//        System.out.println(csc.getAllEvents());
        csc.followEvent("2");

        return null;

    }
}