package com.example.pierrickvinot.projetandroid.models;

import java.util.List;

/**
 * Created by pierrick.vinot on 19/10/16.
 */

public class Message {
    private String message;
    private String login;
    private String uuid;
    private List<String> images;
    private List<Attachment> attachments;



    public Message(String message, String user, String uuid, List<String> image) {
        this.message = message;
        this.login = user;
        this.uuid = uuid;
        this.images = image;
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getImage() {
        if(images!=null) {
            return images.get(0);
        }
        return null;
    }

    public void setImage(List<String> image) {
        this.images = image;
    }

}
