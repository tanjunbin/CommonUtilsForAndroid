package com.tan.lib_utils_android.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtils {

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener);
    }

    //键盘显示监听
    public static void observeSoftKeyBoard(final Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - rect.bottom;

                if (Build.VERSION.SDK_INT >= 20) {
                    // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
                    keyboardHeight = keyboardHeight - getSoftButtonsBarHeight(activity);

                }

                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    listener.onSoftKeyBoardChange(keyboardHeight, !hide, this);
                }

                previousKeyboardHeight = height;

            }
        });
    }

    /**
     * 底部虚拟按键栏的高度
     * @return
     */
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        }
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    /**
     * 打卡软键盘
     *
     * @param mView    输入框
     * @param mContext 上下文
     */
    public static void openKeyBoard(View mView, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mView, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mView    输入框
     * @param mContext 上下文
     */
    public static void closeKeyBoard(View mView, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
    }
}
