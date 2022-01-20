package com.cranaya.biblioteca.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("android:imageUrl")
    public static void setImageUrl(ImageView imageUrl, String url){
        try {
            imageUrl.setAlpha(0f);
            Picasso.get().load(url).noFade().into(imageUrl, new Callback() {
                @Override
                public void onSuccess() {
                    imageUrl.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception e){

        }
    }


}
