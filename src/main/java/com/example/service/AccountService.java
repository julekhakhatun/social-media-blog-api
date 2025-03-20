package com.example.service;

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
     * @param account
     * @return
     */
    public Account createAccount(Account account) throws InvalidAccountException, DuplicateUsernameException {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new InvalidAccountException("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new InvalidAccountException("Password must be at least 4 characters long");
        }
        if (accountRepository.findByUserName(account.getUsername()) != null) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    /*
     * public Account loginAccount(Account account) {
     * return accountRepository.save(account);
     * }
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
