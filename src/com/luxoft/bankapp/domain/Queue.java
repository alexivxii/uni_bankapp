package com.luxoft.bankapp.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue {

    private List<Email> emails = Collections.synchronizedList(new LinkedList<Email>());

    public void add(Email email){
        emails.add(email);
    }

    public boolean isEmpty() {
        return emails.isEmpty();
    }

    public int getQueueSize(){
        return emails.size();
    }

    public Email removeEmail(){

        //primim primul mail din coada si il stergem din lista din queue
        if(emails.size()>0){
            return emails.remove(0);
        }

        //list is empty
        return null;
    }

}
