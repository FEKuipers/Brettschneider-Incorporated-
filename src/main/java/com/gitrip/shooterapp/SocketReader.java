package com.gitrip.shooterapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class SocketReader implements Runnable{
    private boolean running;
    private List<ISocketListener> socketListeners;
    private ObjectInputStream inputStream;

    public SocketReader(List<ISocketListener> socketListeners, ObjectInputStream inputStream) {
        running = true;
        this.socketListeners = socketListeners;
        this.inputStream = inputStream;
    }

    @Override
    public void run(){
        while(running) {
            try {
                TestMessage testMessage = (TestMessage) inputStream.readObject();
                System.out.println("reading input ...");
                if (testMessage != null) {

                }

                Thread.sleep(2000);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
    
    public synchronized <T> void updateListeners(T update) {
        for(ISocketListener listener: socketListeners) {
            listener.receive("Update from socketreader " + update.getContents());
        }
    }
}
