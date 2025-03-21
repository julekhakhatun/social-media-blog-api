package com.example.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * This query is written in Spring Data's query language providing - JPQL, or Java Persistence Query Language.
     * The process of writing a query looks similar to a SQL statement, although the SELECT
     * keyword may be absent. When the SELECT keyword is absent, JPQL will convert rows of the ResultSet directly
     * into the model class that the Repository is built for.
     */

    /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param username the username of the account.
     * @return all accounts with the username provided as parameter.
     */
    @Query("FROM Account WHERE username = :usernameVar")
    Optional<Account> findByUserName(@Param("usernameVar") String userName);

    /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param accountId the account id of the account.
     * @return all accounts with the account id provided as parameter.
     */

    @Query("FROM Account WHERE accountId = :accountIdVar")
    List<Account> findById(@Param("accountIdVar") int accountId);
}
