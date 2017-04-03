package com.example.emile1.findaparty.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emile1 on 03/04/2017.
 */

public class ParticipantAdapter extends ArrayAdapter<Participant>{

    public ParticipantAdapter(Context context, List<Participant> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_participant, parent, false);
        }
        ParticipantViewHolder viewHolder = (ParticipantViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ParticipantViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.participant_name);
            viewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.participant_circle_image);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Participant participant = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(participant.getName());
        viewHolder.avatar.setImageResource(R.drawable.amumu460);
        return convertView;
    }


    private class ParticipantViewHolder {
        private TextView name;
        private CircleImageView avatar;
    }
}
