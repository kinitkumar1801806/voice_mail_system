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
public class AttachAdapter extends RecyclerView.Adapter<AttachAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String>Attach;
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

    public AttachAdapter(Context mContext,ArrayList<String>Attach) {
        this.mContext = mContext;
        this.Attach=Attach;
    }

    @NonNull
    @Override
    public AttachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_attachment, parent, false);
        return new AttachAdapter.ViewHolder(view,mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull AttachAdapter.ViewHolder holder, int position) {
        holder.attach_name.setText(Attach.get(position));
    }

    @Override
    public int getItemCount() {
        return Attach.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView attach_name;
        public ImageView remove;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            attach_name=itemView.findViewById(R.id.name);
            remove=itemView.findViewById(R.id.remove);
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
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.removeItem(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }
}

