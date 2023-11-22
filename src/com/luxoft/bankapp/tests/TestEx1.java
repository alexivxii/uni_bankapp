package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.domain.BankReport;
import java.util.*;

public class TestEx1 {

    @Test
    public void testBankReport() throws ClientExistsException {
        Bank bank = new Bank();
        Client client1 = new Client("Smith John", Gender.MALE,"Bucharest");
        client1.addAccount(new SavingAccount(1, 1000.0));
        client1.addAccount(new CheckingAccount(2, 1000.0, 100.0));

        Client client2 = new Client("Smith Michelle", Gender.FEMALE,"Bucharest");
        client2.addAccount(new SavingAccount(3, 2000.0));
        client2.addAccount(new CheckingAccount(4, 1500.0, 200.0));

        Client client3 = new Client("Parker Peter", Gender.MALE,"New York");
        client3.addAccount(new SavingAccount(5, 5000.0));
        client3.addAccount(new CheckingAccount(6, 4000.0, 300.0));

        BankService.addClient(bank, client1);
        BankService.addClient(bank, client2);
        BankService.addClient(bank, client3);

        BankReport report1 = new BankReport();

        //Testing

        //Get number of clients test
        assertEquals(3,report1.getNumberOfClients(bank));

        //Get number of accounts test
        assertEquals(6, report1.getNumberOfAccounts(bank));

        //Display clients sorted by name alphabetically
        report1.getClientsSorted(bank);

        //Get total sum in accounts in the bank
        //delta = precizia
        assertEquals(14500,report1.getTotalSumInAccounts(bank),0.01);

        //Get accounts sorted by sum
        Set<Account> expectedAccountsSorted = new HashSet<Account>();
        expectedAccountsSorted.add(new SavingAccount(1, 1000.0));
        expectedAccountsSorted.add(new CheckingAccount(2, 1000.0, 100.0));
        expectedAccountsSorted.add(new CheckingAccount(4, 1500.0, 200.0));
        expectedAccountsSorted.add(new SavingAccount(3, 2000.0));
        expectedAccountsSorted.add(new CheckingAccount(6, 4000.0, 300.0));
        expectedAccountsSorted.add(new SavingAccount(5, 5000.0));
        //assertEquals(new SavingAccount(2, 1000.0),new CheckingAccount(2, 1000.0, 100.0));
        //de aici outputu e ca sunt diferite
        System.out.println(expectedAccountsSorted.size());
        assertEquals(expectedAccountsSorted,report1.getAccountSortedBySum(bank));

        //Get the sum of all bank credits provided by the bank
        assertEquals(600,report1.getBankCreditSum(bank),0.01);


        //Get Customer Accounts in form of MAP
        Map<Client,Collection<Account>> accMap = new HashMap<Client,Collection<Account>>();
        accMap.put(client1,client1.getAccounts());
        accMap.put(client2,client2.getAccounts());
        accMap.put(client3,client3.getAccounts());
        assertEquals(accMap,report1.getCustomerAccounts(bank));

        //Display clients for each city(cities in alphabetical order)
        report1.getClientsByCity(bank);
    }

}
