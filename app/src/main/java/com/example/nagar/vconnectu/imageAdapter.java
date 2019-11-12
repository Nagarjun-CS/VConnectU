package com.example.nagar.vconnectu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ImageViewHolder> {

    private Context mcontext;
    private List<Upload> muploads;

    public imageAdapter(Context context,List<Upload> uploads)
    {
        mcontext = context;
        muploads = uploads;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = muploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mcontext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageViewName);



    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView imageViewName;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView2);
            imageViewName = itemView.findViewById(R.id.imageView2);


        }
    }
}
