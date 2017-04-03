package com.example.emile1.findaparty.Activity;

import java.text.ParseException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emile1 on 03/04/2017.
 */

public class Participant {
    public String name;
    public CircleImageView profilePicture;

    public Participant(String name, CircleImageView profilePicture){
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getName(){
        return this.name;
    }

}
