package com.example.pierrickvinot.projetandroid.models;

/**
 * Created by pierrick.vinot on 21/10/16.
 */
public class Attachment {
    private String mimeType;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
