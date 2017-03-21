package com.example.emile1.findaparty.Activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by Emile1 on 21/03/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.LanViewHolder> {

    private List<Lan> lans;
    public CardViewAdapter(List<Lan> lans){
        this.lans = lans;
    }
    @Override
    public void onBindViewHolder(LanViewHolder lanViewHolder, int position) {
        Lan lan = lans.get(position);
        lanViewHolder.date.setText(lan.getDate());
        lanViewHolder.startTime.setText(lan.getStartTime());
        lanViewHolder.endTime.setText(lan.getEndTime());
        lanViewHolder.maxSpots.setText(String.format(Locale.getDefault(),"%d",lan.getMaxSpots()));
        lanViewHolder.participants.setText(String.format(Locale.getDefault(),"%d",lan.getNbrParticipants()));
    }

    @Override
    public int getItemCount() {
        return lans.size();
    }

    @Override
    public LanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                        from(parent.getContext()).
                        inflate(R.layout.row_lan,parent,false);


        return new LanViewHolder(itemView);
    }

    public static class LanViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView startTime;
        public TextView endTime;
        public TextView maxSpots;
        public TextView participants;

        public LanViewHolder(View v) {
            super(v);
            date =  (TextView) v.findViewById(R.id.date);
            startTime = (TextView)  v.findViewById(R.id.startTime);
            endTime = (TextView)  v.findViewById(R.id.endTime);
            maxSpots = (TextView) v.findViewById(R.id.maxSpots);
            participants = (TextView) v.findViewById(R.id.participants);
        }
    }

}
