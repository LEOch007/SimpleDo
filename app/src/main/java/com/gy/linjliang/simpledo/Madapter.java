package com.gy.linjliang.simpledo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 2018/1/7.
 */

public class Madapter extends RecyclerView.Adapter<Madapter.ViewHolder>{
    private List<item> mitem;
    private OnItemClickListener mOnItemClickListener=null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView mcontent;
        TextView time;
        ImageView isfinish;

        public ViewHolder(View itemView) {
            super(itemView);
            icon=(ImageView) itemView.findViewById(R.id.itemicon);
            mcontent=(TextView) itemView.findViewById(R.id.textconent);
            time=(TextView) itemView.findViewById(R.id.textlabel);
            isfinish=(ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    public Madapter(List<item> g){
        mitem=g;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        item it=mitem.get(position);
        holder.mcontent.setText(it.getContent());
        String mtime=it.getFinishyear()+"-"+it.getFinishmonth()+"-"+it.getFinishday()+" "+it.getFinishhour()+"时: "+it.getFinishmin()+"分";
        holder.time.setText(mtime);
        switch(it.getLabel()){
            case "working":
                holder.icon.setImageResource(R.drawable.working);
                break;
            case "studying":
                holder.icon.setImageResource(R.drawable.studying);
                break;
            case "living":
                holder.icon.setImageResource(R.drawable.shopping);
                break;
        }

        switch(it.getIsfinish()){
            case "0":
                holder.isfinish.setImageResource(R.mipmap.clock_icon);
                break;
            case "1":
                holder.isfinish.setImageResource(R.mipmap.tablefinish);
                break;
        }

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onClick(pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onLongClick(pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mitem.size();

    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int postion);
    }

    public void setOnitemClickListener(OnItemClickListener onitemClickListener){
        mOnItemClickListener=onitemClickListener;
    }
}
