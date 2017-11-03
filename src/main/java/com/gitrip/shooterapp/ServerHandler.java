package com.gitrip.shooterapp;

import com.almasb.fxgl.service.Net;

public class ServerHandler implements Runnable{
    private KeepRunning keepRunning;
    private Net net;

    public ServerHandler(KeepRunning keepRunning, Net net) {
        this.keepRunning = keepRunning;
        this.net = net;
    }

    @Override
    public void run() {
        while(keepRunning.doI()) {
            System.out.println("Running server");
            net.getConnection().ifPresent(conn -> {
                conn.send(new TestMessage());
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!net.getConnection().isPresent()){
                System.out.println("stopping thread");
                keepRunning.setKeepRunning(false);
            }
        }
    }
}
