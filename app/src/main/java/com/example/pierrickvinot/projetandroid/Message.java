package com.example.pierrickvinot.projetandroid;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class Message {
    private String message;
    private String login;
    private String uuid;



    public Message(String message, String user, String uuid) {
        this.message = message;
        this.login = user;
        this.uuid = uuid;
    }
    public Message(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
