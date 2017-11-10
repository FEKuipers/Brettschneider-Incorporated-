package com.gitrip.shooterapp;

import com.almasb.fxgl.entity.GameEntity;

public class ClientState {
    private GameEntity serverPlayer;
    private GameEntity clientPlayer;
    private GameEntity me;

    public ClientState(GameEntity serverPlayer, GameEntity clientPlayer, GameEntity me) {
        this.serverPlayer = serverPlayer;
        this.clientPlayer = clientPlayer;
        this.me = me;
    }

    public GameEntity getServerPlayer() {
        return serverPlayer;
    }

    public GameEntity getClientPlayer() {
        return clientPlayer;
    }

    public GameEntity getMe() {
        return me;
    }
}
