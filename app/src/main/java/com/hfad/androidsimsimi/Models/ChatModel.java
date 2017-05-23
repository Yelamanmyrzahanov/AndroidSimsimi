package com.hfad.androidsimsimi.Models;

/**
 * Created by yelaman on 13.05.17.
 */

public class ChatModel {
    public String message;
    public Boolean isSend;

    public ChatModel(String message, Boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public ChatModel(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }
}
