package com.example.moneyjars.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyjars.R;
import com.example.moneyjars.entity.Jar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddSelectJarAdapter extends RecyclerView.Adapter<AddSelectJarAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Jar> list;
    public Callback callback;
    private boolean checked = true;

    public AddSelectJarAdapter(Context context, ArrayList<Jar> list, Callback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_select_jar,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivJars;
        private TextView tvName;
        private EditText tvPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivJars = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvNameJars);
            tvPercent = itemView.findViewById(R.id.tvPercent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (callback != null){
                        callback.onClick(position);
                        if (list.get(position).isChecked()){
                            list.get(position).setChecked(false);
                            ivJars.setBackground(null);
                        }
                        else {
                            list.get(position).setChecked(true);
                            ivJars.setBackground(context.getResources().getDrawable(R.drawable.custom_icon_jar));
                        }
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bindData(int position) {
            Jar jar = list.get(position);
            if (jar != null){
                tvName.setText(jar.getNameJar());
                Picasso.with(context)
                        .load(jar.getAvatar())
                        .into(ivJars);
                tvPercent.setText(jar.getPercent() + " %");
                if (jar.isChecked()){
                    ivJars.setBackground(context.getResources().getDrawable(R.drawable.custom_icon_jar));
                }
            }
        }
    }

    public interface Callback{
        void onClick(int position);
    }
}
