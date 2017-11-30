package com.branter.jiadongyan.branter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gridView1;
    private Button buttonPublish;
    private Button buttonCancel;
    private Button startbtn, endbtn;
    private TextView startDate,endDate;
    private EditText eventTitle;
    private EditText eventContent;
    private int startYear,startMonth,startDay, endYear,endMonth,endDay;
    final int start_DATE_DIALOG = 1;
    final int end_DATE_DIALOG = 2;
    private final int IMAGE_OPEN = 1;
    private String pathImage;
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;
    private DatePickerDialog.OnDateSetListener startdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;
            display(startDate, startYear,startMonth,startDay);
        }
    };

    private DatePickerDialog.OnDateSetListener enddateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            endYear = year;
            endMonth = monthOfYear;
            endDay = dayOfMonth;
            display(endDate, endYear,endMonth, endDay);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_create_event);

        gridView1 = (GridView) findViewById(R.id.gridView1);
        getSupportActionBar().setTitle("New Event");


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);


        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if( imageItem.size() == 4) {
                    Toast.makeText(CreateEventActivity .this, "pics is exceed 3", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) {
                    Toast.makeText(CreateEventActivity .this, "add pics", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);

                }
                else {
                    dialog(position);

                }
            }
        });


        buttonPublish = (Button) findViewById(R.id.buttonpublish);
        buttonPublish.setTag(1);
        buttonPublish.setOnClickListener(CreateEventActivity.this);
        buttonCancel = (Button) findViewById(R.id.buttoncancel);
        buttonCancel.setTag(2);
        buttonCancel.setOnClickListener(CreateEventActivity.this);

        startbtn = (Button) findViewById(R.id.startDateChoose);
        startDate = (TextView) findViewById(R.id.startDate);
        startbtn.setTag(3);
        startbtn.setOnClickListener(CreateEventActivity.this);

        final Calendar ca = Calendar.getInstance();
        startYear = ca.get(Calendar.YEAR);
        startMonth = ca.get(Calendar.MONTH);
        startDay = ca.get(Calendar.DAY_OF_MONTH);

        endbtn = (Button) findViewById(R.id.endDateChoose);
        endDate = (TextView) findViewById(R.id.endDate);
        endbtn.setTag(4);
        endbtn.setOnClickListener(CreateEventActivity.this);

        endYear = ca.get(Calendar.YEAR);
        endMonth = ca.get(Calendar.MONTH);
        endDay = ca.get(Calendar.DAY_OF_MONTH);

        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventContent = (EditText) findViewById(R.id.eventContent);

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Intent intent=new Intent(this,MainActivity.class);
        switch (tag) {
            case 1:
                Log.e("I am in create event", "LOL");
//                Event newEvent = new Event("","","");
//                GridTest newGridTest = new GridTest();
//                newGridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
//                newGridTest.setContent(eventContent.getText().toString());
//                newGridTest.setTime("From " + startDate.getText().toString() + " to " + endDate.getText().toString());
//                newGridTest.setImage(pathImage);
//                newGridTest.setEventTitle(eventTitle.getText().toString());
//                MainActivity.listgrid.add(newGridTest);

                final String[] att = new String[] {"title", "contents","from","to","lat","lng","imageURL"};
                final String[] value = new String[] {eventTitle.getText().toString(),eventContent.getText().toString(),
                        startDate.getText().toString(), endDate.getText().toString(), "0","0",""};
                Thread one = new Thread() {
                    public void run() {
                        try {
                            CSC client = new CSC();
                            client.createEvent(att,value);
                        } catch(Exception v) {
                        }
                    }
                };
                one.start();
                try {
                    one.join();
                } catch (InterruptedException e) {

                }

                startActivity(intent);
                break;

            case 2:
                startActivity(intent);
                break;

            case 3:
                showDialog(start_DATE_DIALOG);
                break;

            case 4:
                showDialog(end_DATE_DIALOG);
                break;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case start_DATE_DIALOG:
                return new DatePickerDialog(this, startdateListener, startYear, startMonth, startDay);
            case end_DATE_DIALOG:
                return new DatePickerDialog(this, enddateListener, endYear, endMonth, endDay);

        }
        return null;
    }


    public void display(TextView v, int month, int day, int year) {
        v.setText(new StringBuffer().append(month + 1).append("-").append(day).append("-").append(year).append(" "));
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {

                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);

                if (null == cursor) {
                    return;
                }

                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(pathImage)){
            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView1.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();

            pathImage = null;
        }
    }


    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity .this);
        builder.setMessage("Are you sure to remove this picture");
        builder.setTitle("Waring");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
