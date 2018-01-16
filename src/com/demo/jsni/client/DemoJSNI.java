package com.demo.jsni.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoJSNI implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final LoginServiceAsync greetingService = GWT.create(LoginService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Login");
        RootPanel.get("sendButtonContainer").add(sendButton);

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Notification");
        dialogBox.setAnimationEnabled(true);

        final Button closeButton = new Button("Close");
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler {
            public void onClick(ClickEvent event) {
                if (checkLogin(Account.getEmail(), Account.getPassword())) {
                    GWT.log("Username: " + Account.getEmail() + "<br>Password: " + Account.getPassword());
                    sendDataToServer(Account.getEmail(), Account.getPassword());
                }
            }

            private void sendDataToServer(String email, String password) {
                // we send the data to the server.
                sendButton.setEnabled(false);
                serverResponseLabel.setText("");
                greetingService.loginServer(email, password, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        serverResponseLabel.setHTML("Something wrong!");
                    }

                    @Override
                    public void onSuccess(String result) {
                        dialogBox.setText("Login successfully");
                        serverResponseLabel.setHTML(result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                        GWT.log("Received resonse from server!");
                    }
                });
                GWT.log("Waiting for resonse from server!");
            }
        } MyHandler myHandler = new MyHandler();
        sendButton.addClickHandler(myHandler);
    }

    /**
     * Check syntax email and password
     * @param email
     * @param password
     * @return true if valid and reverse
     */
    public native boolean checkLogin(String email, String password) /*-{
		console.log("IN checklogin");
		console.log("Username: " + email + "<br>Password: " + password);
		if (!@com.demo.jsni.client.DemoJSNI::validateEmail(Ljava/lang/String;)(email)) {
			alert("Please input correct email.");
			return false;
		} else if (password.length < 8) {
			alert("Please input password at least 8 characters.");
			return false;
		}
		console.log("OUT checklogin");
		return true;
    }-*/;

    /**
     * Validate email input
     * @param email
     * @return true if valid and reverse
     */
    public static boolean validateEmail(String email) {
        RegExp regExp = RegExp.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return regExp.test(email);
    }
}
