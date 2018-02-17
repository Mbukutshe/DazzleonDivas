package com.payghost.dazzleondivas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by EDU01 on 5/3/2017.
 */

public class noticeViewHolder extends RecyclerView.ViewHolder{
    public TextView time,message;

    public  noticeViewHolder(View v){
       super(v);

        message =(TextView)v.findViewById(R.id.displayMessage);
        time = (TextView)v.findViewById(R.id.messageTime);

    }

}
