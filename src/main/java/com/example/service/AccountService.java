package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidCredentialsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    // JPA data repository
    AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    /***
     * Registers a user account by persiting it to the database. This method will either throw an exception or return null if any of
     * these failure cases occur: account is null (return null), username already exists (throw an exception),
     * username is blank (return null), password is not greater than 4 characters (return null) 
     * @param account
     * @return the persisted account
     */
    public Account registerUser(Account account) {
        if(account == null) // check if the account is null first
            return null;

        // throw an exception if the username is already present in the database
        if(this.repository.findByUsername(account.getUsername()).isPresent())
            throw new DuplicateUsernameException("Username already exists. Please choose another username");

        // make sure the input username text is not blank and the password is longer than four characters
        if (account.getUsername().isBlank() || account.getPassword().length() <= 4)
            return null;
        
        return this.repository.save(account); // persist the new account to the database    
    }

    /***
     * Logs in a user by searching for it in the database using the username and password of the account. This method will 
     * throw an exception if any of these failure cases occur: username is null, password is null, account is not in
     * the database
     * @param username
     * @param password
     * @return the logged in account
     */
    public Account loginUser(String username, String password) {
        if(username == null || password == null) // make sure the username and password are not null
            throw new InvalidCredentialsException("username and password cannot be null");

        // return the account found using the username and password, otherwise throw an exception
        return this.repository.findByUsernameAndPassword(username, password).orElseThrow(() -> new InvalidCredentialsException("Username or Password is Incorrect"));
    }
}
