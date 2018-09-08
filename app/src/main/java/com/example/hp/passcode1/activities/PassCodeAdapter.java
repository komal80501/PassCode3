package com.example.hp.passcode1.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.passcode1.R;

import java.util.List;

class PassCodeAdapter extends RecyclerView.Adapter<PassCodeAdapter.PassCodeViewHolder>{

    private Context mCtx;
    private List<PassCode> passCodeList;
    private RecyclerViewClickListener mClickListener;

    public PassCodeAdapter(Context mCtx, List<PassCode> passCodeList,RecyclerViewClickListener listener) {
        this.mCtx = mCtx;
        this.passCodeList = passCodeList;
        this.mClickListener=listener;
    }

    @NonNull
    @Override
    public PassCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mCtx);
        View view=layoutInflater.inflate(R.layout.recyclerview_row,null);
        PassCodeViewHolder holder= new PassCodeViewHolder(view,mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PassCodeViewHolder passCodeViewHolder, int position) {
        PassCode passCode=passCodeList.get(position);
        passCodeViewHolder.tvPname.setText(passCode.getpName());
        passCodeViewHolder.tvType.setText(passCode.getpType());
        passCodeViewHolder.tvStartTime.setText(passCode.getStartTime());
        passCodeViewHolder.tvEndTime.setText(passCode.getEndTime());
        passCodeViewHolder.tvDays.setText(passCode.getDays());

    }

    @Override
    public int getItemCount() {
        return passCodeList.size();
    }

    public class PassCodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPname,tvType,tvStartTime,tvEndTime,tvDays;
        RecyclerViewClickListener recyclerViewClickListener;

        public PassCodeViewHolder(View itemView ,RecyclerViewClickListener listener) {
            super(itemView);
            tvPname=itemView.findViewById(R.id.txtViewRPname);
            tvType=itemView.findViewById(R.id.txtRViewPtype);
            tvStartTime=itemView.findViewById(R.id.txtViewRStartTime);
            tvEndTime=itemView.findViewById(R.id.txtViewREndTime);
            tvDays=itemView.findViewById(R.id.txtViewRDays);
            recyclerViewClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClick(v, getAdapterPosition());

        }



    }
}
