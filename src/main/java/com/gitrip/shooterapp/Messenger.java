package com.gitrip.shooterapp;

import com.almasb.fxgl.net.NetworkConnection;
import com.gitrip.shooterapp.message.Message;
import com.gitrip.shooterapp.message.RepositionMessage;

import java.util.HashMap;
import java.util.Optional;

public class Messenger {
    private Optional<NetworkConnection> connection;
    private ClientHandler clientHandler;
    private ServerHandler serverHandler;

    public Messenger(Optional<NetworkConnection> connection, ClientHandler clientHandler, ServerHandler serverHandler) {
        this.connection = connection;
        this.clientHandler = clientHandler;
        this.serverHandler = serverHandler;

        initializeParsers();
    }

    public final synchronized void sendMessage(Message message) {
        connection.ifPresent(conn -> conn.send(message));
    }

    public void stopMessenger() {
        connection.ifPresent(conn -> conn.close());
    }

    protected void initializeParsers() {
        //reset parser list
        connection.get().setParsers(new HashMap<>());

        //add parsers
        connection.get().addParser(RepositionMessage.class, e -> clientHandler.handleRepositionMessage(e));

    }
}
