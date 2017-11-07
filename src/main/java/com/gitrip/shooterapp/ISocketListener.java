package com.gitrip.shooterapp;

public interface ISocketListener <T> {
    void receive(T input);
}
