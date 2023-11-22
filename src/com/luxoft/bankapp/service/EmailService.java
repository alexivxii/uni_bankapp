package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.domain.Queue;

public class EmailService implements Runnable {

    private Queue queue = new Queue();
    private int sentEmails = 0;

    private boolean isServiceOpen = true;

    public int getSentEmailsNr() {
        return sentEmails;
    }

    private void sendEmail(){
        sentEmails++;
    }

    public EmailService(){
        new Thread(this).start();
    }

    @Override
    public void run(){

        while(true){

            //daca serviciul e inchis, nu mai continuam sa trimitem mail-urile
            if(isServiceOpen==false) {
                System.out.println("Service is closed, no more sending emails");
                return;
            }

            try{
                synchronized (queue){
                    queue.wait();
                }
            } catch (InterruptedException e){
                e.printStackTrace();
                return;
            }
        }

    }

    public void sendNotificationEmail(Email email){
        //Todo: Queue.add email

        if(isServiceOpen){
            queue.add(email);
            //adaugam la numarul de mail uri trimise
            sendEmail();
            System.out.println("Sending Email");
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
