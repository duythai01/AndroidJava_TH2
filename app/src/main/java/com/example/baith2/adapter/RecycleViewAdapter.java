package com.example.baith2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baith2.R;
import com.example.baith2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends  RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Item> list;
    private ItemListener itemListener;

    public RecycleViewAdapter(List<Item> list) {
        list = new ArrayList<>();
    }

    public RecycleViewAdapter() {
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public List<Item> getList() {
        return list;
    }

    public Item getItem(int Position) {
        return list.get(Position);
    }
    public interface ItemListener {
        void onItemClick(View view,int positon);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.date.setText(item.getDate());
        holder.price.setText(item.getPrice());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class  HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title, category, price, date;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            category = itemView.findViewById(R.id.tvCategory);
            price = itemView.findViewById(R.id.gia);
            date = itemView.findViewById(R.id.ngay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
