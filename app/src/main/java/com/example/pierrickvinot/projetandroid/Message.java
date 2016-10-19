package com.example.pierrickvinot.projetandroid;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class Message {
    private String message;
    private String login;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Message(String message, String user) {
        this.message = message;
        this.login = user;
    }
    public Message(){}


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
