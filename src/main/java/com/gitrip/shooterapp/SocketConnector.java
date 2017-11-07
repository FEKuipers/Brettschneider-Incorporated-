package com.gitrip.shooterapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketConnector {
    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private SocketReader socketReader;
    private boolean isServer;
    private static final String ipAddress = "localhost";
    private static final short port = 1234;

    List<ISocketListener> listeners;

    public SocketConnector() {
        listeners = new ArrayList<>();
    }

    public void openConnection() {
        try{
            socket = new Socket(ipAddress, port);
            isServer = false;
        }catch(IOException e){
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                isServer = true;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        socketReader = new SocketReader(listeners, input);
        new Thread(socketReader).start();
    }

    public <T> void sendMessage(T data){
        try {
            output.writeObject(data);

            if(isServer) {
                socke
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addListener(ISocketListener listener) {
        listeners.add(listener);
    }

    public void closeConnection() {
        try{
            socketReader.stop();
            __.doIfNotNull(socket, s -> s.close());
            __.doIfNotNull(serverSocket, s -> s.close());
            __.doIfNotNull(input, i -> i.close());
            __.doIfNotNull(output, o -> o.close());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
