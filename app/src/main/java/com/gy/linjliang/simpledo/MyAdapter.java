package com.gy.linjliang.simpledo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2018/1/6.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private Context mContext;
    private List<itemdate> itemdates;
    private OnItemClickLitener mOnItemClickLitener;

    //创建构造参数
    public MyAdapter(Context context , List<itemdate> datas){
        this.mContext = context;
        this.itemdates = datas;
        inflater = LayoutInflater.from(context);
    }
    //必须重载的三大函数
    @Override
    public int getItemCount() {
        return itemdates.size();
    }
    //创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_datetask, parent , false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    //接口类
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view , int position);
    }
    public void setOnItemClickLitener(OnItemClickLitener mmOnItemClickLitener)
    {
        this.mOnItemClickLitener = mmOnItemClickLitener;
    }
    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //内容赋值
        holder.tv1.setText(itemdates.get(position).getDate());
        holder.tv2.setText(itemdates.get(position).getDatecon());
        holder.tv3.setText(itemdates.get(position).getDatenum());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    /*                -- Viewholder类 --                  */
    //自定义Viewholder类
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv1;
        TextView tv2;
        TextView tv3;
        View v;
        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            tv1 = (TextView) itemView.findViewById(R.id.date);
            tv2 = (TextView) itemView.findViewById(R.id.datecont);
            tv3 = (TextView) itemView.findViewById(R.id.datenum);
        }
    }
}
