package com.liuyue.hospitaltemperature.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuyue.hospitaltemperature.R;
import com.liuyue.hospitaltemperature.a_model.RecordModel;

import java.util.List;


/**
 * Created by kys_31 on 2017/12/18.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHoler> {

    private List<RecordModel> list;
    private Context context;

    public RecordAdapter(Context context, List<RecordModel> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHoler holer = new MyViewHoler(LayoutInflater.from(context).inflate(R.layout.item_record_view, parent, false));
        return holer;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, final int position) {
        if (list.get(position).getState()<1){
            holder.mIvState.setImageResource(R.drawable.blow_icon);
        }
        holder.mTvValue.setText(list.get(position).getValue());
        holder.mTvTime.setText(list.get(position).getTime());
        holder.mTvLocation.setText(list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHoler extends RecyclerView.ViewHolder{
        public ImageView mIvState;
        public TextView mTvValue;
        public TextView mTvTime;
        public TextView mTvLocation;
        public MyViewHoler(View itemView) {
            super(itemView);
            mIvState = itemView.findViewById(R.id.iv_state);
            mTvValue = itemView.findViewById(R.id.tv_value);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mTvLocation = itemView.findViewById(R.id.tv_location);

        }
    }
}
