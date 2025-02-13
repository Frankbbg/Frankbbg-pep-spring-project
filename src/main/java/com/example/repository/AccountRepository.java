package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /***
     * Retrieve a user account using the username and password of the account.
     * @param username
     * @param password
     * @return the account as an Optional
     */
    Optional<Account> findByUsernameAndPassword(String username, String password);

    /***
     * Retrieves an account using the username. Since usernames must be unique, this method will never return a list of 
     * accounts.
     * @param username
     * @return the account as an Optional
     */
    Optional<Account> findByUsername(String username);
}