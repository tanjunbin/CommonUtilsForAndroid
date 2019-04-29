package com.tan.lib_utils_android.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;

public class GlideUtils {

    /**
     * 基本用法
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 对象
     */
    public static void baseGlide(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 尽管及时取消不必要的加载是很好的实践，但这并不是必须的操作。
     * 实际上，当 Glide.with() 中传入的 Activity 或 Fragment 实例销毁时，Glide 会自动取消加载并回收资源。
     *
     * @param context
     * @param imageView
     */
    public static void clearGlide(Context context, ImageView imageView) {
        Glide.with(context).clear(imageView);
    }

    /**
     *  含有默认图片和加载失败图片
     * @param context
     * @param url
     * @param imageView
     * @param defaultImage
     * @param errorImage
     */
    public static void commonFunGlide(Context context, String url, ImageView imageView,
                                      int defaultImage, int errorImage){
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(defaultImage))
                .apply(new RequestOptions().error(errorImage))
                .into(imageView);
    }


    /**
     *  将Glide获取的图片转换成圆角并进行宽高的等比缩放
     * @param context
     * @param url
     * @param imageView
     */
    public static void transformGlide(Context context,String url,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(new RequestOptions().fitCenter())
                .apply(RequestOptions.bitmapTransform(new BitmapTransformation() {
                    @Override
                    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
                        int x = (toTransform.getWidth() - size) / 2;
                        int y = (toTransform.getHeight() - size) / 2;

                        // TODO this could be acquired from the pool too
                        Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);

                        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);

                        Canvas canvas = new Canvas(result);
                        Paint paint = new Paint();
                        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                        paint.setAntiAlias(true);
                        float r = size / 2f;
                        canvas.drawCircle(r, r, r, paint);
                        return result;
                    }

                    @Override
                    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

                    }
                }))
                .into(imageView);
    }
}
