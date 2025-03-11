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

    public List<Accounts> getAllAccounts(){
        return accountRepository.findAll();

    }

    public Optional<Account> getUserById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
