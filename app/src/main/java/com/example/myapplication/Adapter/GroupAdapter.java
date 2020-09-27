package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.ArrayList;
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private Context mContext;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    public static final int MSG_TYPE_IMAGE_LEFT=2;
    public static final int MSG_TYPE_IMAGE_RIGHT=3;
    private ArrayList<String>Groups;
    public GroupAdapter(Context mContext,ArrayList<String>Groups) {
        this.mContext = mContext;
        this.Groups=Groups;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.group_lists, parent, false);
            return new GroupAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image,image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {
       return 1;
    }
}

