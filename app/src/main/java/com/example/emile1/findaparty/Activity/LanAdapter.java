package com.example.emile1.findaparty.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.emile1.findaparty.R;

import java.util.List;

/**
 * Created by Emile1 on 16/02/2017.
 */
public class LanAdapter extends ArrayAdapter<Lan> {

    //tweets est la liste des models à afficher
    public LanAdapter(Context context, List<Lan> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_lan,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.startTime = (TextView) convertView.findViewById(R.id.startTime);
            viewHolder.endTime = (TextView) convertView.findViewById(R.id.endTime);
            viewHolder.maxSpots = (TextView) convertView.findViewById(R.id.maxSpots);
            viewHolder.participants = (TextView) convertView.findViewById(R.id.participant);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Lan lan = getItem(position);

        //il ne reste plus qu'à remplir notre vue
//        viewHolder.date.setText(lan.getDate());
//        viewHolder.startTime.setText(lan.getStartTime());
//        viewHolder.endTime.setText(lan.getEndTime());
//        viewHolder.maxSpots.setText(lan.getMaxSpots());
//        viewHolder.participants.setText(lan.getParticipants());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView date;
        public TextView startTime;
        public TextView endTime;
        public TextView maxSpots;
        public TextView participants;
    }
}
