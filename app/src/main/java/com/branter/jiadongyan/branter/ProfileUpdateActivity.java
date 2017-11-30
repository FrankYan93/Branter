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

public class ProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener{
    
    private GridView gridView1;              //网格显示缩略图
    private Button buttonPublish;            //发布按钮
    private Button buttonCancel;
    private Button startbtn;
    private TextView startDate,endDate;
    private EditText username;
    private EditText gender;
    private int startYear,startMonth,startDay;
    final int start_DATE_DIALOG = 1;
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private Bitmap bmp;                      //导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        /*
         * 防止键盘挡住输入框
         * 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile_update);
        //获取控件对象
        gridView1 = (GridView) findViewById(R.id.profile_submit_image);

        /*
         * 载入默认图片添加图片加号
         * 通过适配器实现
         * SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为布局
         */
        //获取资源图片加号
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});
        /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
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

        /*
         * 监听GridView点击事件
         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
         */
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_OPEN);
                //通过onResume()刷新数据

            }
        });


        buttonPublish = (Button)findViewById(R.id.profile_submit_update_button);
        buttonPublish.setTag(1);
        buttonPublish.setOnClickListener(ProfileUpdateActivity.this);
        buttonCancel = (Button) findViewById(R.id.profile_submit_cancel_button);
        buttonCancel.setTag(2);
        buttonCancel.setOnClickListener(ProfileUpdateActivity.this);

        startbtn = (Button) findViewById(R.id.profile_submit_pickdate_tag);
        startDate = (TextView) findViewById(R.id.profile_submit_birthday_tag);
        startbtn.setTag(3);
        startbtn.setOnClickListener(ProfileUpdateActivity.this);

        final Calendar ca = Calendar.getInstance();
        startYear = ca.get(Calendar.YEAR);
        startMonth = ca.get(Calendar.MONTH);
        startDay = ca.get(Calendar.DAY_OF_MONTH);


        username = (EditText) findViewById(R.id.profile_submit_username_tag);
        gender = (EditText) findViewById(R.id.profile_submit_gender_tag);

    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Intent intent=new Intent(this,MainActivity.class);
        switch (tag) {
            case 1:
                Log.e("I am in create event", "LOL");
//                Event newEvent = new Event("","","");
                //TODO: create new event to data servive
                GridTest newGridTest = new GridTest();
                newGridTest.setHeadphoto("http://img3.imgtn.bdimg.com/it/u=3367770910,1075442079&fm=21&gp=0.jpg");
                newGridTest.setContent(username.getText().toString());
                newGridTest.setTime("From " + startDate.getText().toString() + " to " + endDate.getText().toString());
                newGridTest.setImage(pathImage);
                newGridTest.setEventTitle(gender.getText().toString());
                MainActivity.listgrid.add(newGridTest);

                startActivity(intent);
                break;

            case 2:
                startActivity(intent);
                break;

            case 3:
                showDialog(start_DATE_DIALOG);
                break;

        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case start_DATE_DIALOG:
                return new DatePickerDialog(this, startdateListener, startYear, startMonth, startDay);

        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display(TextView v, int month, int day, int year) {
        v.setText(new StringBuffer().append(month + 1).append("-").append(day).append("-").append(year).append(" "));
    }


    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }  //end if 打开图片
    }

    //刷新图片
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
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }

    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileUpdateActivity.this);
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
