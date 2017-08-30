package com.recyclerviewdragtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hyc on 2017/8/29 17:37
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    static int imageLength = 0;
    private List<String> mData;
    private Context mContext;
    public static final int MAX_COUNT = 9;

    public ImageAdapter(List<String> mData , Context context,int length){
        this.mContext = context;
        this.mData = mData;
        imageLength = length;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if (position >= MAX_COUNT){
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.imageView.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(mData.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            if (imageLength != 0){
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                params.width = imageLength ;
                params.height = imageLength;
                imageView.setLayoutParams(params);
            }
        }
    }




}
