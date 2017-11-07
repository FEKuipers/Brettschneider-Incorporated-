package com.gitrip.shooterapp;

public class TestClass implements ISocketListener<String> {
    public static void main(String... args) {
        SocketConnector sock = new SocketConnector();

        try{
            sock.openConnection();
            sock.addListener(new TestClass());
            sock.sendMessage(new TestMessage("MyContenta"));
            sock.sendMessage(new TestMessage("MyContent 2b"));

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }finally {
            sock.closeConnection();
        }

    }

    @Override
    public void receive(String input) {
        System.out.println(input);
    }
}
