package com.example.blurdatingapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FunctionUtil {
    public static void setFaceImage(Context context, Uri uri, ImageView imageView){
        Glide.with(context).load(uri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
