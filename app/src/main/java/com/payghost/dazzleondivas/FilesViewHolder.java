package com.payghost.dazzleondivas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Payghost on 9/5/2017.
 */

public class FilesViewHolder extends RecyclerView.ViewHolder{

    public TextView filename;
    public ImageView fileurl;


    public  FilesViewHolder(View v){
        super(v);

        filename =(TextView)v.findViewById(R.id.txtfile);
        fileurl = (ImageView)v.findViewById(R.id.ivfile);

    }

}
