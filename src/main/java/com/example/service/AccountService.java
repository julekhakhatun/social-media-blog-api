package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        return accountRepository.save(account);
    }

}
