package com.example.emile1.findaparty.Activity;

import java.io.Serializable;
import java.text.ParseException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emile1 on 03/04/2017.
 */

public class Participant {
    public String name;
    public String id;
    public CircleImageView profilePicture;

    public Participant(String id,String name, CircleImageView profilePicture){
        this.id= id;
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public String getName(){
        return this.name;
    }
    public String getId(){return  this.id;}

}
