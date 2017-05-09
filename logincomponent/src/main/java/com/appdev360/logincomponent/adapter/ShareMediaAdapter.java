package com.appdev360.logincomponent.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appdev360.logincomponent.R;

import java.util.List;

/**
 * Courtesy by abubaker on 4/7/17.
 */

public class ShareMediaAdapter extends RecyclerView.Adapter<ShareMediaAdapter.MyViewHolder> {

    private List<ResolveInfo> mDataSet;
    private Context context;
    private ClickListener itemClickListener;

    public ShareMediaAdapter(Context context, List<ResolveInfo> list){
        mDataSet = list;
        this.context = context;

    }

    public void setClickListener(ClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ClickListener {

        void onItemClicked(int itemPosition);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_media_item_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        final ResolveInfo object = mDataSet.get(position);
        holder.name.setText(object.loadLabel(context.getPackageManager()));
        holder.icon.setImageDrawable(object.loadIcon(context.getPackageManager()));
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView icon;
        private RelativeLayout layout;
        public View mainView;

        public MyViewHolder(View v) {
            super(v);
            mainView = v;
            name = (TextView) v.findViewById(R.id.media_name);
            icon = (ImageView) v.findViewById(R.id.media_icon);
            layout = (RelativeLayout)v.findViewById(R.id.relative_layout);

        }


    }
}
