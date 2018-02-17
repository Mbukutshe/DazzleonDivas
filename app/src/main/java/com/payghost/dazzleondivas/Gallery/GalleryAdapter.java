package com.payghost.dazzleondivas.Gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.payghost.dazzleondivas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 21428 on 10/18/2017.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryviewHolder>{

    public List<GalleryItems> list ;
    private Context context;

    public GalleryAdapter(Context context, List<GalleryItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public GalleryviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_cardview,null);
        GalleryviewHolder viewHolder = new GalleryviewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final GalleryviewHolder holder, final int position) {

        Picasso.with(context).load(list.get(position).getImage()).resize(150,120).centerCrop().into(holder.image);

        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.translate);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);

        if (holder.image.getDrawable() == null){
            holder.image.setBackgroundResource(R.drawable.diva);
        }

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
