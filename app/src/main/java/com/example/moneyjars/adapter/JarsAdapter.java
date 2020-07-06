package com.example.moneyjars.adapter;

import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JarsAdapter extends RecyclerView.Adapter<JarsAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Jar> list;
    public Callback callback;

    public JarsAdapter(Context context, ArrayList<Jar> list, Callback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jar, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivJars;
        private TextView tvName, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivJars = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvNameJars);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (callback != null) {
                        callback.onClick(position);
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bindData(int position) {
            Jar jar = list.get(position);
            if (jar != null) {
                tvName.setText(jar.getNameJar());
                Picasso.with(context)
                        .load(jar.getAvatar())
                        .into(ivJars);
                int amount = Integer.parseInt(jar.getIncome()) - Integer.parseInt(jar.getExpense());
                if (amount < 0) amount = 0;
                tvAmount.setText(amount + " " + context.getResources().getString(R.string.VND));
            }
        }
    }

    public interface Callback {
        void onClick(int position);
    }
}
