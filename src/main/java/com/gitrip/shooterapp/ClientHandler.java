package com.gitrip.shooterapp;

import com.almasb.fxgl.entity.GameEntity;
import com.gitrip.shooterapp.message.RepositionMessage;
import javafx.application.Platform;
import javafx.geometry.Point2D;

public class ClientHandler {
    private ClientState clientState;

    public ClientHandler(ClientState clientState) {
        this.clientState = clientState;
    }

    public void handleRepositionMessage(RepositionMessage repositionMessage) {
        Platform.runLater(() -> {
            GameEntity toMove = repositionMessage.getPlayer().equals("client") ? clientState.getClientPlayer() : clientState.getServerPlayer();
            toMove.setPosition(new Point2D(repositionMessage.getxPos(), repositionMessage.getyPos()));
        });
    }
}
