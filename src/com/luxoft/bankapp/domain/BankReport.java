package com.luxoft.bankapp.domain;
import com.luxoft.bankapp.domain.Bank;
import java.util.*;

//ToDo: task 1.1
public class BankReport {

    //ToDo: needs test
    int getNumberOfClients(Bank bank){
        return bank.getClients().size();
    }

    //ToDo: needs test
    int getNumberOfAccounts(Bank bank){
        int nrAccounts = 0;

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            for(Account account : accountSet){
                nrAccounts++;
            }

        }

        return nrAccounts;
    }

    //clients sorted by name and prints
    SortedSet getClientsSorted(Bank bank) {

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        SortedSet clientsSorted = new TreeSet<Client>(Comparator.comparing(Client::getName));
        clientsSorted.addAll(clientSet);

        System.out.println(clientsSorted);

        return clientsSorted;

    }

    double getTotalSumInAccounts(Bank bank){

        double balanceAllAccounts = 0.0;

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            for(Account account : accountSet){
                balanceAllAccounts = balanceAllAccounts + account.getBalance();
            }

        }

        return balanceAllAccounts;

    }

    SortedSet getAccountSortedBySum(Bank bank){

        SortedSet accountsSorted = new TreeSet<Account>(Comparator.comparing(Account::getBalance));

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            accountsSorted.addAll(accountSet); //addAll appends every set of accounts from every client

        }

        return accountsSorted;

    }

    //ToDo: ?? asa se face creditu?
    double getBankCreditSum(Bank bank){

        double creditSum = 0.0;

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            for(Account account : accountSet){
                //we have to differentiate between the two types of accounts
                //to do so, we check if the current account is a checking one
                //in order to get the overdraft
                if (account instanceof CheckingAccount) {
                    CheckingAccount checkingAccount = (CheckingAccount) account;
                    creditSum = creditSum + checkingAccount.getOverdraft();
                }
            }

        }

        return creditSum;

    }


    Map<Client,Collection<Account>> getCustomerAccounts(Bank bank){

        Map<Client,Collection<Account>> clientAccountsMap = new HashMap<Client,Collection<Account>>();

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();
        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            clientAccountsMap.put(client,accountSet);

        }

        return clientAccountsMap;

    }

    Map<String, List<Client>> getClientsByCity(Bank bank){

        Map<String, List<Client>> cityClientsMap = new HashMap<String, List<Client>>();

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();
        List<Client> tempList = new ArrayList<Client>();
        SortedSet<String> allCities = new TreeSet<String>();


        for(Client client : clientSet){

            allCities.add(client.getCity());

            cityClientsMap.put(client.getCity(), new ArrayList<Client>());

            tempList = cityClientsMap.get(client.getCity()); //toti clientii din orasul acestui client
            tempList.add(client);
            //i use put again to overwrite
            cityClientsMap.put(client.getCity(), tempList);
            tempList.clear();
        }

//        for(Client client : clientSet){
//            tempList = cityClientsMap.get(client.getCity()); //toti clientii din orasul acestui client
//            tempList.add(client);
//            //i use put again to overwrite
//            cityClientsMap.put(client.getCity(), tempList);
//            tempList.clear();
//        }


        //print clients for each city (cities in alphabetical order)
        for (String city : allCities){
            System.out.println(city);
            System.out.println(cityClientsMap.get(city));
        }

        return cityClientsMap;

    }



}