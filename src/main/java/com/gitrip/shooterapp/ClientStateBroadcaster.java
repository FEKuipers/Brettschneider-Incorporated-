package com.gitrip.shooterapp;

import com.gitrip.shooterapp.message.RepositionMessage;

public class ClientStateBroadcaster implements Runnable{
    private ClientState clientState;
    private boolean continueBroadcasting;
    private Messenger messenger;

    public ClientStateBroadcaster(ClientState clientState, Messenger messenger) {
        this.clientState = clientState;
        this.messenger = messenger;
        continueBroadcasting = true;
    }

    public void stopBroadcasting() {
        continueBroadcasting = false;
    }

    @Override
    public void run() {
        while(continueBroadcasting) {
            String playerstring = clientState.getClientPlayer() == clientState.getMe() ? "client" : "server";
            messenger.sendMessage(new RepositionMessage(playerstring, clientState.getMe().getX(), clientState.getMe().getY()));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
