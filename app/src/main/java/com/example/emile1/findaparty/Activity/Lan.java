package com.example.emile1.findaparty.Activity;

import java.io.Serializable;


public class Lan implements Serializable{
    private String date;
    private String startTime;
    private String endTime;
    private int maxSpots;
    private int nbrParticipants;
    private String idOwner;
    private String nameOwner;
    private String idLan;

    public Lan(String idLan,String idOwner,String date, String startTime, String endTime, int maxSpots, int nbrParticipants){
        this.idLan=idLan;
        this.idOwner = idOwner;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxSpots = maxSpots;
        this.nbrParticipants = nbrParticipants;
    }

    public  String getIdLan(){return idLan;}

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

    public void setIdLan(String idLan) {this.idLan = idLan;}

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
