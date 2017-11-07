package com.gitrip.shooterapp;

import java.util.function.Function;

public class __ {

    static public <T> void doIfNotNull(T object, UnsafeFunction<T> action) throws Exception {
        if(object != null) {
            action.apply(object);
        }
    }
}
