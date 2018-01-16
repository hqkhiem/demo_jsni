package com.demo.jsni.server;

import com.demo.jsni.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    
    public String loginServer(String user, String password) throws IllegalArgumentException {
        try {
            // Check on DB
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        return "Hi " + user + ",<br> How are you today?";
    }

}