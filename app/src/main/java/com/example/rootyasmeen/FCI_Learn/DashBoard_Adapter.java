package com.example.rootyasmeen.FCI_Learn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fci on 20/02/18.
 */

public class DashBoard_Adapter extends RecyclerView.Adapter<DashBoard_Adapter.DHolder> {
    private List<ListFile> listItems;
    private Context context;

    public DashBoard_Adapter(List<ListFile> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public DHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_item,parent,false);
        return new DashBoard_Adapter.DHolder(v1);
    }

    @Override
    public void onBindViewHolder(DHolder holder, int position) {
        final ListFile listFile = listItems.get(position);
        holder.name.setText( listFile.getName() );
        holder.time.setText( listFile.getTime() );
        holder.name.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listFile.getUrl()));
                context.startActivity(browserIntent);
            }
        } );
        holder.time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listFile.getUrl()));
                context.startActivity(browserIntent);
            }
        } );

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class DHolder extends RecyclerView.ViewHolder{
        TextView time, name;
        public DHolder(View itemView) {
            super( itemView );
            time = (TextView)itemView.findViewById( R.id.file_time );
            name = (TextView)itemView.findViewById( R.id.file_name );
        }
    }
}
