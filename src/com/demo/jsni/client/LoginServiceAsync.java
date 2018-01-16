package com.demo.jsni.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LoginServiceAsync {
    void loginServer(String user, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
