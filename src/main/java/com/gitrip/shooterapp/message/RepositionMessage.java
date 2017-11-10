package com.gitrip.shooterapp.message;

public class RepositionMessage implements Message {
    private String player;
    private double xPos;
    private double yPos;

    public RepositionMessage(String player, double xPos, double yPos) {
        this.player = player;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getPlayer() {
        return player;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}
