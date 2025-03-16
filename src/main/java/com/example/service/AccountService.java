package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;


import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    
    /**
     * @param account
     * @return
     */
    public Account createAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()){
            throw new InvalidAccountException("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4){
            throw new InvalidAccountException("Password must be at least 4 characters long");
        }
        if (accountRepository.findByUserName(account.getUsername()) != null){
            throw new DuplicateFormatFlagsException("Username already exists");
        }

        account.setAccountId(UUID.randomUUID().toString());
        return accountRepository.save(account);
    }

    /*public Account loginAccount(Account account) {
        return accountRepository.save(account);
    }*/

    public Account loginAccount(String username, String password) {
      Account account = accountRepository.findByUserName(username)
                .orElseThrow(() -> new InvalidAccountException("Invalid username or password"));

      if (!account.getPassword().equals(password)) {
        throw new InvalidAccountException("Invalid username or password");
      }
        return account;
    }

}
