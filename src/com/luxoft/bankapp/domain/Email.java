package com.luxoft.bankapp.domain;

import java.util.ArrayList;

public class Email {

    private Client sender;
    private ArrayList<Client> receivers;

    private String title;
    private String body;

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public ArrayList<Client> getReceivers() {
        return receivers;
    }

    public void setReceivers(ArrayList<Client> receivers) {
        this.receivers = receivers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addReceiver(Client client){
        this.receivers.add(client);
    }

    @Override
    public String toString() {
        return "Email{" +
                "sender=" + sender +
                ", receivers=" + receivers +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
