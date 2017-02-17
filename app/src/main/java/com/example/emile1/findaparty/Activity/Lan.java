package com.example.emile1.findaparty.Activity;

/**
 * Created by Emile1 on 16/02/2017.
 */

public class Lan {
    private String date;
    private String startTime;
    private String endTime;
    private int maxSpots;
    private int nbrParticipants;
    private String idOwner;
    private String nameOwner;

    public Lan(String date, String startTime, String endTime, int maxSpots, int nbrParticipants){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxSpots = maxSpots;
        this.nbrParticipants = nbrParticipants;
    }

    public String getDate(){
        return date;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public int getMaxSpots(){
        return maxSpots;
    }

    public int getNbrParticipants(){
        return nbrParticipants;
    }

    public String getIdOwner(){
        return idOwner;
    }

    public String getNameOwner(){
        return nameOwner;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setMaxSpots(int maxSpots){
        this.maxSpots = maxSpots;
    }

    public void setNbrParticipants(int nbrParticipants){
        this.nbrParticipants = nbrParticipants;
    }

    public void setIdOwner(String idOwner){
        this.idOwner = idOwner;
    }

    public void setNameOwner(String nameOwner){
        this.nameOwner = nameOwner;
    }
}
