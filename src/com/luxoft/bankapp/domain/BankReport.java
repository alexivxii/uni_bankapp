package com.luxoft.bankapp.domain;
import com.luxoft.bankapp.domain.Bank;
import java.util.*;

//ToDo: task 1.1
public class BankReport {

    //ToDo: needs test
    public int getNumberOfClients(Bank bank){
        return bank.getClients().size();
    }

    //ToDo: needs test
    public int getNumberOfAccounts(Bank bank){
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
    public SortedSet<Client> getClientsSorted(Bank bank) {

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        SortedSet clientsSorted = new TreeSet<Client>(Comparator.comparing(Client::getName));
        clientsSorted.addAll(clientSet);

        System.out.println(clientsSorted);

        return clientsSorted;

    }

    public double getTotalSumInAccounts(Bank bank){

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

    public SortedSet<Account> getAccountSortedBySum(Bank bank){

        //SortedSet<Account> accountsSorted = new TreeSet<Account>(Comparator.comparingDouble(Account::getBalance));
        SortedSet<Account> accountsSorted = new TreeSet<>(new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return a2.getBalance() < a1.getBalance() ? 1 : -1;
            }
        });

        //ToDo: trb sa ma uit la semnatura sa fiu mai atent
        //chiar daca ii zic sa fie hashset, de fapt get clients imi da un unmodifiable set
        //era mai safe daca ii dadeam direct bank.getClients()
        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();

        Set<Account> accountSet = new HashSet<Account>();
        Set<Account> temp = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();
            //System.out.printf("adaugam %d conturi in setul cu size %d\n", accountSet.size(),temp.size());
            temp.addAll(accountSet); //addAll appends every set of accounts from every client
            //System.out.printf("size acum este %d\n",temp.size());

        }

        accountsSorted.addAll(temp);

        Iterator<Account> iterator = accountsSorted.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            System.out.println("Account Balance: " + account.getBalance());
        }

        System.out.println(accountsSorted.size());
        //System.out.println(accountsSorted);

        return accountsSorted;

    }

    //ToDo: ?? asa se face creditu?
    public double getBankCreditSum(Bank bank){

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


    public Map<Client,Collection<Account>> getCustomerAccounts(Bank bank){

        Map<Client,Collection<Account>> clientAccountsMap = new HashMap<Client,Collection<Account>>();

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();
        Set<Account> accountSet = new HashSet<Account>();

        for(Client client : clientSet){
            accountSet = client.getAccounts();

            clientAccountsMap.put(client,accountSet);

        }

        //System.out.println(clientAccountsMap);

        return clientAccountsMap;

    }

    public Map<String, List<Client>> getClientsByCity(Bank bank){

        Map<String, List<Client>> cityClientsMap = new HashMap<String, List<Client>>();

        Set<Client> clientSet = new HashSet<Client>();
        clientSet = bank.getClients();
        List<Client> tempList = new ArrayList<Client>();
        SortedSet<String> allCities = new TreeSet<String>();


        for(Client client : clientSet){

            allCities.add(client.getCity());

            if (cityClientsMap.containsKey(client.getCity())==false){
                cityClientsMap.put(client.getCity(), new ArrayList<Client>());
            }

            tempList = cityClientsMap.get(client.getCity()); //toti clientii din orasul acestui client
            tempList.add(client);

            cityClientsMap.replace(client.getCity(), tempList);
        }

        //print clients for each city (cities in alphabetical order)
        for (String city : allCities){
            System.out.println(city);
            System.out.println(cityClientsMap.get(city));
        }

        return cityClientsMap;

    }



}