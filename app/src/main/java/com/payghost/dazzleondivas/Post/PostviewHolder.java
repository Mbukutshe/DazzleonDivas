package com.payghost.dazzleondivas.Post;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.payghost.dazzleondivas.R;

public class PostviewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    TextView tvtitle;
    TextView tvdescription;
    TextView more;
    ImageView share;

    public  PostviewHolder(View v){
        super(v);
        image =(ImageView) v.findViewById(R.id.post_image);
        tvtitle = (TextView) v.findViewById(R.id.ptitle);
        tvdescription = (TextView) v.findViewById(R.id.pdescription);
        more = (TextView) v.findViewById(R.id.more);
        share =(ImageView) v.findViewById(R.id.ads_share);
    }
}