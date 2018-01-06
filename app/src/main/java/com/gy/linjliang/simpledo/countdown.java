package com.gy.linjliang.simpledo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class countdown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<RepoInfo> repoList;

        public MyAdapter(List<RepoInfo> repoList){
            this.repoList = repoList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private View item_view;
            private TextView repoName_item;
            private TextView language_item;
            private TextView description_item;

            public ViewHolder(View view)
            {
                super(view);
                item_view = view;
                repoName_item = (TextView) view.findViewById(R.id.repoName_item);
                language_item = (TextView) view.findViewById(R.id.language_item);
                description_item = (TextView) view.findViewById(R.id.description_item);
            }
        }

        //创建item视图，并返回相应的viewHolder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_listitem,parent,false);
            final ViewHolder holder = new ViewHolder(view);

            holder.item_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getAdapterPosition();
                    repoList.remove(position);
                    notifyItemRemoved(position);
                    return true;
                }
            });

            return holder;
        }

        //绑定数据到正确的item视图上
        @Override
        public void onBindViewHolder(ViewHolder holder,int position)
        {
            RepoInfo repo = repoList.get(position);
            holder.repoName_item.setText(repo.getName());
            holder.language_item.setText(repo.getLanguage());
            holder.description_item.setText(repo.getDescription());
        }

        @Override
        public int getItemCount() {
            return repoList.size();
        }
    }
    */
}
