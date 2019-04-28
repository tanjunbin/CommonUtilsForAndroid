package com.tan.lib_utils_android.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class ToastUtils {

    private static Toast toast;
    private static Handler toastHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (!(msg.obj instanceof Context)) return;
            Context context = (Context) msg.obj;
            int resid = msg.what;
            int length = msg.arg1;
            makeText(context, resid, length == 0 ? LENGTH_SHORT : LENGTH_SHORT).show();
        }
    };

    public static synchronized void showOnUIThread(Context context, int resid, int duration) {
        Message msg = new Message();
        msg.obj = context;
        msg.what = resid;
        msg.arg1 = duration;
        toastHandler.sendMessage(msg);
    }

    public static void showCenter(Context context, String msg) {
        ToastUtils.showCenter(context, msg, LENGTH_LONG);
    }

    public static void showCenter(Context context, String msg, int duration) {
        ToastUtils.show(context, msg, duration, Gravity.CENTER);
    }

    public static void show(Context context, String msg) {
        ToastUtils.show(context, msg, LENGTH_LONG);
    }

    public static void show(Context context, String msg, int duration) {
        ToastUtils.show(context, msg, duration, -99);
    }

    public static void show(Context context, String msg, int duration, int gravity) {
        if (toast == null) {
            toast = makeText(context, msg, duration);
            if (gravity > -99) {
                toast.setGravity(gravity, 0, 0);
            }
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
            if (gravity > -99) {
                toast.setGravity(gravity, 0, 0);
            }
        }
        toast.show();
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
