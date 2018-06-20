package com.example.rootyasmeen.FCI_Learn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fci on 18/02/18.
 */

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.Holder> {
    private List<PostItem> listItems;
    private Context context1;

    public Post_Adapter(List<PostItem> listItems, Context context1) {
        this.listItems = listItems;
        this.context1 = context1;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row_item,parent,false);
        return new Post_Adapter.Holder(v1);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final PostItem post = listItems.get(position);
        holder.set_post.setText(post.getPost());
        holder.time.setText(post.getTime());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView set_post, time;
        public Holder(View itemView) {
            super( itemView );
            set_post = (TextView)itemView.findViewById( R.id.set_post );
            time = (TextView)itemView.findViewById( R.id.time );
        }
    }
}
