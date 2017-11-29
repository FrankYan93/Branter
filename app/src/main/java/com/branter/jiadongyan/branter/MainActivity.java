package com.branter.jiadongyan.branter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Hashtable<String,String> accounts = new Hashtable<>();

    private List<GridTest> listgrid;
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private String imgs1;
    private  String imgs3;
    private  String imgs2;
    private String imgs4;

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userName = SaveSharedPreference.getUserName(MainActivity.this);
        if(userName == null || userName.length() == 0)
        {   super.onCreate(savedInstanceState);
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

            listgrid = new ArrayList<GridTest>();
            init();
            initData();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        TextView username = (TextView) findViewById(R.id.usernameM);
        username.setText(userName);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            // TODO: start account info activity
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
        imgs3="http://t2.fansimg.com/uploads2011/02/userid290276time20110205120020.jpg#" +
                "http://image81.360doc.com/DownloadImg/2015/01/2113/49316679_9.jpg#" +
                "http://image.tianjimedia.com/uploadImages/2014/133/11/EN2I6768CHU1_1000x500.jpg#"+
                "http://pic72.nipic.com/file/20150716/6659253_104414205000_2.jpg#"+
                "http://pic36.nipic.com/20131222/10558908_214221305000_2.jpg";
        imgs4 = "http://h.hiphotos.baidu.com/zhidao/pic/item/5243fbf2b21193133f9f1e3967380cd790238d5f.jpg";
        GridTest gridTest = null;
        for(int i = 0;i<=3;i++){
            gridTest = new GridTest();
            switch (i){
                case 0:gridTest.setEventTitle("Cat discovery");
                    gridTest.setHeadphoto("http://cdn.duitang.com/uploads/item/201412/12/20141212184514_BJjWy.jpeg");
                    gridTest.setContent("This is the cat event!......");
                    gridTest.setTime("11/25/2017 - 11/28/2017");
                    gridTest.setImage(imgs1);
                    break;
                case 1:
                    gridTest.setEventTitle("Enjoy Japanese Food");
                    gridTest.setHeadphoto("http://cdn.duitang.com/uploads/item/201501/19/20150119171935_ZkRsZ.thumb.224_0.jpeg");
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "clicked on" + (position + 1) + "item", Toast.LENGTH_LONG).show();
            }
        });
    }
}
