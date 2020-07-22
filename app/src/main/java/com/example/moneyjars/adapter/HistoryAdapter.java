package com.example.moneyjars.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyjars.R;
import com.example.moneyjars.entity.Jar;
import com.example.moneyjars.entity.NoteHistory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public Context context;
    public ArrayList<NoteHistory> list;
    private int type = 1 ;
    private Callback callback;

    public HistoryAdapter(Context context, ArrayList<NoteHistory> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(list.size() -1-position);
    }

    @Override
    public int getItemCount() {
        if (list.size() <3){
            return list.size();
        }
        else {
            return 3;
        }
    }

    public void setListHistory(ArrayList<NoteHistory> listHistory){
        list = listHistory;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivJars;
        private TextView tvName,tvAmount, tvType,tvTimeStamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivJars = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvNameJars);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvType = itemView.findViewById(R.id.tvType);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);

            if (type == 0 && callback!= null){
                int position = getAdapterPosition();
                callback.onClick(position);
            }

        }

        public void bindData(int position){
            NoteHistory history = list.get(position);
            if (history != null){
                tvName.setText(history.getNameJar());
                tvType.setText(history.getType() == 1 ? "Thu nhập" : "Chi tiêu");
                tvAmount.setText(history.getAmount() +" "+context.getString(R.string.VND));
                if (history.getType() == 1){
                    tvType.setTextColor(context.getResources().getColor(R.color.green_light));
                    tvAmount.setTextColor(context.getResources().getColor(R.color.green_light));
                }
                else {
                    tvType.setTextColor(context.getResources().getColor(R.color.red));
                    tvAmount.setTextColor(context.getResources().getColor(R.color.red));
                }
                tvTimeStamp.setText(history.getTimeStamp());
                Picasso.with(context)
                        .load(history.getAvatarJar())
                        .into(ivJars);
            }
        }
    }

    public interface Callback{
        void onClick(int position);
    }

}
