package com.example.files_practice.images;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.files_practice.R;
import com.example.files_practice.databinding.ImageItemBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {
    public static final String TAG = ImagesAdapter.class.getSimpleName();
    private List<Image> mImages = new ArrayList<>();
    private Context mContext;

    public ImagesAdapter(List<Image> mImages) {
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ImageItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.image_item,parent,false);
       mContext = parent.getContext();
       return new ImagesViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        setImage(holder,position);
    }

    private void setImage(ImagesViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_error);

        try {
            File file = new File(mImages.get(position).imageFilePath);

            Glide.with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Uri.fromFile(file)).into(holder.binding.image);

        } catch (NullPointerException e) {
            Log.d(TAG, "error displaying contact image with message " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void setImages(List<Image> images) {
        this.mImages = images;
        notifyDataSetChanged();
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageItemBinding binding;

        public ImagesViewHolder(@NonNull ImageItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
