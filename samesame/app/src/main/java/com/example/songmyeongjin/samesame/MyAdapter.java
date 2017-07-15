package com.example.songmyeongjin.samesame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by songmyeongjin on 2017. 6. 7..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewHolder> {

    private List<recyclerItem> listItem;
    private Context mContext;

    public MyAdapter(List<recyclerItem> listitems, Context mContext) {
        this.listItem = listitems;
        this.mContext = mContext;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        final recyclerItem item = listItem.get(position);
        holder.txTitle.setText(item.getTitle());
        holder.txDescription.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public TextView txTitle;
        public TextView txDescription;



        public viewHolder(View itemView) {
            super(itemView);
            txTitle = (TextView)itemView.findViewById(R.id.txtitle);
            txDescription = (TextView)itemView.findViewById(R.id.txdescription);

        }
    }
}
