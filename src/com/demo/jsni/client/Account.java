package com.demo.jsni.client;

public class Account {
    public static native String getEmail()/*-{
        return $doc.getElementById("email").value;
    }-*/;

    public static native String getPassword()/*-{
    return $doc.getElementById("password").value;
}-*/;
}
