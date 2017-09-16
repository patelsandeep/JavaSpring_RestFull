package com.task.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.model.Account;
import com.task.service.AccountService;
import com.task.util.DataConflictException;
import com.task.util.DataNotFindException;
import com.task.util.MissingRequiredFieldException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    /**
     * Find Account by account_id from the Json File.
     *
     * @param account_id is the parameter to getAccount method .
     * @return Account.
     * @throws DataNotFoundException if no Account by account_id exist.
     */
    @Override
    public Account getAccount(Integer account_id) {
        List<Account> accounts = getAllAccount();

        final Account accountToCheck = accounts
                .stream()
                .filter((account) -> Objects.equals(account.getAccount_id(), account_id))
                .findFirst()
                .orElse(null);
        if (accountToCheck == null) {
            throw new DataNotFindException("Account with id " + account_id + "  not found");
        } else {
            return accountToCheck;
        }

    }

    /**
     * Create new Account in Json File.
     *
     * @param account is the parameter to createAccount method .
     * @throws DataConflictException occurs when account_id already exist in Json file.
     */
    @Override
    public void createAccount(Account account) {
        if (((account.getAccount_id() == null)
                || (account.getCustomername() == null || account.getCustomername().isEmpty()))) {
            throw new MissingRequiredFieldException("Customer name and account_id are required fields ");
        } else {
            ObjectMapper mapper = new ObjectMapper();

            List<Account> accounts = getAllAccount();
            for (Account account1 : accounts) {
                System.out.println(
                        account1.getAccount_id() + "com.task.service.impl.AccountServiceImpl.createAccount()" + account
                                .getAccount_id());
                if (account1.getAccount_id().intValue() == account.getAccount_id().intValue()) {
                    System.out.println(
                            account1.getAccount_id() + "com.task.service.impl.AccountServiceImpl.createAccount()" + account
                                    .getAccount_id());
                    throw new DataConflictException("Acccount already exist with id: " + account.getAccount_id());
                }
            }
            accounts.add(account);

            try {
                //Convert object to JSON string and save into file directly

                mapper.writeValue(new File("src/main/resources/accounts.json"), accounts);
                mapper.writeValueAsString(accounts);

            } catch (IOException e) {

                Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    /**
     * Update existing Account in Json File.
     *
     * @param account is the parameter to updateAccount method .
     */
    @Override
    public void updateAccount(Account account) {
        ObjectMapper mapper = new ObjectMapper();
        List<Account> updatedAccounts = getAllAccount()
                .stream()
                .map((accountJson) -> getAccountUpdated(accountJson, account))
                .collect(Collectors.toList());

        File file = new File("src/main/resources/accounts.json");
        try {
            mapper.writeValue(file, updatedAccounts);
        } catch (IOException ex) {
            Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get all Account from Json File.
     *
     * @return List of Account.
     */
    @Override
    public List<Account> getAllAccount() {
        List<Account> accounts = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            JSONArray jArray = (JSONArray) parser.parse(new FileReader("src/main/resources/accounts.json"));
            jArray.stream()
                    .forEach((object) -> {
                        JSONObject accountObj = (JSONObject) object;
                        Optional<String> currency_ = Optional.ofNullable((String) accountObj.get("currency"));
                        Optional<String> amount_ = Optional.ofNullable((String) (accountObj.get("amount") + ""));

                        Account.AccountBuilder account = Account.builder()
                                .account_id(Integer.valueOf(accountObj.get("account_id").toString()))
                                .customername(accountObj.get("customername").toString());
                        if (currency_.isPresent()) {
                            account.currency(currency_.get());
                        }
                        if (amount_.isPresent()) {
                            account.amount(BigDecimal.valueOf(Double.valueOf(amount_.get())));
                        }
                        accounts.add(account.build());
                    });
        } catch (ParseException | IOException ex) {
            Logger.getLogger(AccountServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    /**
     * helper method for updateAccount()
     *
     * @return Updated Account.
     */
    private Account getAccountUpdated(Account accountJson, Account accountRequest) {
        if (accountJson.getAccount_id() == accountRequest.getAccount_id()) {
            if (accountRequest.getCustomername() != null && !accountRequest.getCustomername().equals("")) {
                accountJson.setCustomername(accountRequest.getCustomername());
            }
            if (accountRequest.getAmount() != null) {
                accountJson.setAmount(accountRequest.getAmount());
            }
            if (accountRequest.getCurrency() != null && !accountRequest.getCurrency().equals("")) {
                accountJson.setCurrency(accountRequest.getCurrency());
            }
            return accountJson;
        } else {
            return accountJson;
        }
    }
}
