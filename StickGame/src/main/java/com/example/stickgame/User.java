package com.example.stickgame;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int highestberries  = 0;

    private int currentBerries = 0;

    transient private int playerx;
    transient private int playery;
//    private static User instance;
    public void setUsername(String name){
        this.username = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setHighestberries(int berries){
        this.highestberries = berries;
    }

    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public int getHighestberries(){return this.highestberries;}

    public void setCurrentBerries(int currentBerries) {
        this.currentBerries = currentBerries;
    }

    public int getCurrentBerries() {
        return currentBerries;
    }

    public int getPlayerx() {
        return playerx;
    }

    public int getPlayery() {
        return playery;
    }

    public void setPlayerx(int playerx) {
        this.playerx = playerx;
    }

    public void setPlayery(int playery) {
        this.playery = playery;
    }

//    private User(){
//
//    }
//    public static User getInstance(){
//        if(instance == null){
//            instance = new User();
//        }
//        return instance;
//    }
}
