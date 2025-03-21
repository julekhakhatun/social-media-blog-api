package com.example.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidAccountException;
import com.example.exception.InvalidLoginException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * In this method, we are creating a new account. The registration will be successful if and only if the username
     * is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. 
     *If all these conditions are met, the response body should contain a JSON of the Account, including its accountId.
     * @param account
     * @return a persisted account entity
     */
    public Account createAccount(Account account) throws InvalidAccountException, DuplicateUsernameException {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new InvalidAccountException("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new InvalidAccountException("Password must be at least 4 characters long");
        }
        Optional<Account> existingAccount = accountRepository.findByUserName(account.getUsername());
        if (existingAccount.isPresent()) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    /** 
     * In this method we verify the login of the endpoint.The login will be successful if and only if 
     * the username and password provided in the request body JSON match a real account existing on the database.
     * @param username, password
     * @return a persisted account entity.
    */

    public Account loginAccount(String username, String password)
            throws InvalidAccountException, InvalidLoginException {
        Account account = accountRepository.findByUserName(username)
                .orElseThrow(() -> new InvalidAccountException("Invalid username"));

        if (!account.getPassword().equals(password)) {
            throw new InvalidLoginException("Invalid username or password");
        }
        return account;
    }

}
