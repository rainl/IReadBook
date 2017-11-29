package com.wl.rxjsoup;

public class RxUtils {

    public static boolean isEmpty(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
}
