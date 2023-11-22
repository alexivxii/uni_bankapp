package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;
import java.util.stream.Collectors;

public class BankReportStreams {

    //ToDo: needs test
    public int getNumberOfClients(Bank bank){
        return bank.getClients().size();
    }

    //ToDo: needs test
    public int getNumberOfAccounts(Bank bank){
        //aplicam stream
        //pentru fiecare client mapam cate account uri are
        //si le insumam
        return bank.getClients().stream().mapToInt(client -> client.getAccounts().size()).sum();
    }

    //clients sorted by name and prints
    public SortedSet<Client> getClientsSorted(Bank bank) {

//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//
//        SortedSet clientsSorted = new TreeSet<Client>(Comparator.comparing(Client::getName));
//        clientsSorted.addAll(clientSet);
//
//        System.out.println(clientsSorted);
//
//        return clientsSorted;

        //aplicam stream
        //aplicam comparator
        //retinem rezultatul intermediar intr-o colectie

        System.out.println("Output getClientsSorted Stream");

        //bank.getClients().stream().sorted(Comparator.comparing(Client::getName)).collect(Collectors.toCollection(TreeSet::new)).forEach(client -> System.out.println("Client: " + client.getName()));;

        //return bank.getClients().stream().sorted(Comparator.comparing(Client::getName)).collect(Collectors.toCollection(TreeSet::new));

        //la functia lambda nu e nevoie de parametru, doar sa treaca rezultatul stream-ului in colectia respectiva
        return bank.getClients().stream().collect(Collectors.toCollection(() -> new TreeSet<Client>(Comparator.comparing(Client::getName))));

    }

    public double getTotalSumInAccounts(Bank bank){

//        double balanceAllAccounts = 0.0;
//
//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//
//        Set<Account> accountSet = new HashSet<Account>();
//
//        for(Client client : clientSet){
//            accountSet = client.getAccounts();
//
//            for(Account account : accountSet){
//                balanceAllAccounts = balanceAllAccounts + account.getBalance();
//            }
//
//        }
//
//        return balanceAllAccounts;

        //flatMap - Returns a stream consisting of the results of replacing each element of
        //this stream with the contents of a mapped stream produced by applying
        //the provided mapping function to each element.
        //dupa ce obtinem un stream cu account uri pentru fiecare client facem suma balance-urilor
        return bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).mapToDouble(Account::getBalance).sum();

    }

    public SortedSet<Account> getAccountSortedBySum(Bank bank){

//        //SortedSet<Account> accountsSorted = new TreeSet<Account>(Comparator.comparingDouble(Account::getBalance));
//        SortedSet<Account> accountsSorted = new TreeSet<>(new Comparator<Account>() {
//            @Override
//            public int compare(Account a1, Account a2) {
//                return a2.getBalance() < a1.getBalance() ? 1 : -1;
//            }
//        });
//
//        //ToDo: trb sa ma uit la semnatura sa fiu mai atent
//        //chiar daca ii zic sa fie hashset, de fapt get clients imi da un unmodifiable set
//        //era mai safe daca ii dadeam direct bank.getClients()
//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//
//        Set<Account> accountSet = new HashSet<Account>();
//        Set<Account> temp = new HashSet<Account>();
//
//        for(Client client : clientSet){
//            accountSet = client.getAccounts();
//            //System.out.printf("adaugam %d conturi in setul cu size %d\n", accountSet.size(),temp.size());
//            temp.addAll(accountSet); //addAll appends every set of accounts from every client
//            //System.out.printf("size acum este %d\n",temp.size());
//
//        }
//
//        accountsSorted.addAll(temp);
//
//        Iterator<Account> iterator = accountsSorted.iterator();
//        while (iterator.hasNext()) {
//            Account account = iterator.next();
//            System.out.println("Account Balance: " + account.getBalance());
//        }
//
//        System.out.println(accountsSorted.size());
//        //System.out.println(accountsSorted);
//
//        return accountsSorted;

        Comparator<Account> comparator = new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return a2.getBalance() < a1.getBalance() ? 1 : -1;
            }
        };

        //la stream-ul de account-uri aplicam comparatorul si colectam rezultatul intermediar
        //return bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).sorted(comparator).collect(Collectors.toCollection(TreeSet::new));

        //la functia lambda nu e nevoie de parametru, doar sa treaca rezultatul stream-ului in colectia respectiva
        return bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).collect(Collectors.toCollection(() -> new TreeSet<Account>(comparator)));
    }

    //ToDo: ?? asa se face creditu?
    public double getBankCreditSum(Bank bank){

//        double creditSum = 0.0;
//
//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//
//        Set<Account> accountSet = new HashSet<Account>();
//
//        for(Client client : clientSet){
//            accountSet = client.getAccounts();
//
//            for(Account account : accountSet){
//                //we have to differentiate between the two types of accounts
//                //to do so, we check if the current account is a checking one
//                //in order to get the overdraft
//                if (account instanceof CheckingAccount) {
//                    CheckingAccount checkingAccount = (CheckingAccount) account;
//                    creditSum = creditSum + checkingAccount.getOverdraft();
//                }
//            }
//
//        }
//
//        return creditSum;

        //filtram stream-ul de account-uri (avem nevoie doar de CheckingAccount)
        //apoi pentru fiecare obtinem overdraft-ul si adaugam la suma
        return bank.getClients().stream().flatMap(client -> client.getAccounts().stream()).
                filter(e -> e instanceof CheckingAccount).mapToDouble(e -> ((CheckingAccount) e).getOverdraft()).sum();

    }


    public Map<Client,Collection<Account>> getCustomerAccounts(Bank bank){

//        Map<Client,Collection<Account>> clientAccountsMap = new HashMap<Client,Collection<Account>>();
//
//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//        Set<Account> accountSet = new HashSet<Account>();
//
//        for(Client client : clientSet){
//            accountSet = client.getAccounts();
//
//            clientAccountsMap.put(client,accountSet);
//
//        }
//
//        //System.out.println(clientAccountsMap);
//
//        return clientAccountsMap;

        //Map<Client,Collection<Account>> clientAccountsMap = new HashMap<Client,Collection<Account>>();
        
        //bank.getClients().stream().peek(e -> clientAccountsMap.put(e,e.getAccounts()));
        Map<Client,Collection<Account>> clientAccountsMap = bank.getClients().stream().collect(Collectors.toMap(client -> client,Client::getAccounts));

        return clientAccountsMap;

    }

    public Map<String, List<Client>> getClientsByCity(Bank bank){

//        Map<String, List<Client>> cityClientsMap = new HashMap<String, List<Client>>();
//
//        Set<Client> clientSet = new HashSet<Client>();
//        clientSet = bank.getClients();
//        List<Client> tempList = new ArrayList<Client>();
//        SortedSet<String> allCities = new TreeSet<String>();
//
//
//        for(Client client : clientSet){
//
//            allCities.add(client.getCity());
//
//            if (cityClientsMap.containsKey(client.getCity())==false){
//                cityClientsMap.put(client.getCity(), new ArrayList<Client>());
//            }
//
//            tempList = cityClientsMap.get(client.getCity()); //toti clientii din orasul acestui client
//            tempList.add(client);
//
//            cityClientsMap.replace(client.getCity(), tempList);
//        }
//
//        //print clients for each city (cities in alphabetical order)
//        for (String city : allCities){
//            System.out.println(city);
//            System.out.println(cityClientsMap.get(city));
//        }
//
//        return cityClientsMap;

        Map<String, List<Client>> cityClientsMap = new HashMap<String, List<Client>>();

        cityClientsMap = bank.getClients().stream().collect(Collectors.groupingBy(Client::getCity));

        return cityClientsMap;

    }

}
