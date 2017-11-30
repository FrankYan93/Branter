package com.branter.jiadongyan.branter;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Jerry on 11/28/17.
 */

public class SysUtils {
    public  static int Dp2Px(Context context, float dp){
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dp * scale + 0.5f);
    }
    public static int getScreenWidth(Activity activity){
        WindowManager windowManager = activity.getWindowManager();

        Display display = windowManager.getDefaultDisplay();

        return display.getWidth();
    }


}
