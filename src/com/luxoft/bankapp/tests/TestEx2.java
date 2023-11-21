package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.EmailService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

public class TestEx2 {

    int nrEmails = 5;

    @Test
    public void testBankReport() throws ClientExistsException {
        Bank bank = new Bank();
        Client client1 = new Client("Smith John", Gender.MALE, "Bucharest");

        Client client2 = new Client("Smith Michelle", Gender.FEMALE, "Bucharest");

        Client client3 = new Client("Parker Peter", Gender.MALE, "New York");

        //Testing

        System.out.println("Start email testing");

        ArrayList<Client> receivers = new ArrayList<Client>();

        receivers.add(client2);
        receivers.add(client3);

        Email email1 = new Email();
        email1.setBody("Mesaj de trimis");
        email1.setTitle("Titlul mail-ului");
        email1.setSender(client1);
        email1.setReceivers(receivers);

        EmailService emailService = new EmailService();

        for(int i = 0; i < nrEmails; i++){
            emailService.sendNotificationEmail(email1);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        //Todo: Close service
        emailService.closeService();

        //trying to send emails but with the service closed
        for(int i = 0; i < nrEmails; i++){
            emailService.sendNotificationEmail(email1);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


        assertEquals(nrEmails,emailService.getSentEmailsNr());

    }

}
