package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.entity.Account;




@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("FROM Account WHERE username = :usernameVar")
    Optional<Account>findByUserName (@Param("usernameVar") String userName);

    
    @Query("FROM Account WHERE accountId = :accountIdVar")
    List<Account> findById (@Param("accountIdVar") int accountId);


    @Query("SELECT * FROM Account")
    long findAllAccounts();


    @Query("INSERT INTO Account(accountId, username, password) VALUES (:accountIdVar, :usernameVar, :passwordVar)")
    List<Account> findInsertAccounts (@Param("accountIdVar") int accountId, @Param("usernameVar") String username, @Param("passwordVar") String password );


    


}
