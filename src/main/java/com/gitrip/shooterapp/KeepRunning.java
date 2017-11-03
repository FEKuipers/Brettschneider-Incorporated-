package com.gitrip.shooterapp;

public class KeepRunning {
    private boolean keepRunning;

    public KeepRunning () {
        keepRunning = true;
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    public boolean doI() {
        return keepRunning;
    }
}
