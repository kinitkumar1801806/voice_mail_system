package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.ArrayList;
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String>Groups;
    private OnItemClickListener mListener;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void removeItem(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }

    public GroupAdapter(Context mContext,ArrayList<String>Groups) {
        this.mContext = mContext;
        this.Groups=Groups;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.group_lists, parent, false);
            return new GroupAdapter.ViewHolder(view,mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
     holder.group_name.setText(Groups.get(position));
    }

    @Override
    public int getItemCount() {
        return Groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView group_name;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            group_name=itemView.findViewById(R.id.group_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.removeItem(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
       return 2;
    }
}

