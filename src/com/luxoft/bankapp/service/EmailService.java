package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.domain.Queue;

import java.sql.SQLOutput;

public class EmailService implements Runnable {

    private Queue queue = new Queue();
    private int sentEmails = 0;

    private boolean isServiceOpen = true;

    public int getSentEmailsNr() {
        return sentEmails;
    }

    private void increaseNrOfSentEmails(){
        sentEmails++;
    }

    public EmailService(){
        new Thread(this).start();
    }

    @Override
    public void run(){

        while(true){

            while(queue.isEmpty()){
                if(isServiceOpen==false) {
                    System.out.println("Service is closed, no more sending emails");
                    return;
                }

                synchronized (queue){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            //adaugam la numarul de mail uri trimise
            increaseNrOfSentEmails();
            Email emailTemp = queue.removeEmail();
            System.out.println("Sending Email: " + emailTemp.getTitle());

        }

    }

    public void sendNotificationEmail(Email email){
        //Todo: Queue.add email

        if(isServiceOpen){
            queue.add(email);
            synchronized (queue){
                queue.notify();
            }
        }

    }

    public void closeService(){

        isServiceOpen = false;

        System.out.println("Service closed");

        synchronized (queue){
            queue.notify();
        }

    }

}
