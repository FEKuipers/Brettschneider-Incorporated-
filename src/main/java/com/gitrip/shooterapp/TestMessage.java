package com.gitrip.shooterapp;

import java.io.Serializable;

public class TestMessage implements Serializable{
    private String contents;

    public String getContents() {
        return contents;
    }

    public TestMessage(String contents){
        this.contents = contents;
    }
}
