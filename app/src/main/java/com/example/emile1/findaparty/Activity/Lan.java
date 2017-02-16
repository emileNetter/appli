package com.example.emile1.findaparty.Activity;

/**
 * Created by Emile1 on 16/02/2017.
 */

public class Lan {
    private String date;
    private String start;
    private String end;
    private int maxPlaces;
    private int participants;
    private String idOwner;
    private String nameOwner;

    public Lan(String date, String start, String end, int maxPlaces, int participants){
        this.date = date;
        this.start = start;
        this.end = end;
        this.maxPlaces = maxPlaces;
        this.participants = participants;
    }
}
