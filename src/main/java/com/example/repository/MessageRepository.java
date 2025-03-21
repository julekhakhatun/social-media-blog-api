package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>  {

    /**
     * This query is written in Spring Data's query language providing - JPQL, or Java Persistence Query Language.
     * You can notice here that the process of writing a query looks similar to a SQL statement, although the SELECT
     * keyword may be absent. When the SELECT keyword is absent, JPQL will convert rows of the ResultSet directly
     * into the model class that the Repository is built for.
     */

     /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param messageId the message id of the message.
     */
    @Modifying
    @Query("DELETE FROM Message WHERE messageId = :messageIdVar")
    int deleteByMessageId (@Param("messageIdVar") int messageId);

    /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param messageId the message id of the message.
     * @return all messages with the id provided as parameter.
     */
    @Query("FROM Message WHERE messageId = :messageIdVar")
    List<Account> findById (@Param("messageIdVar") int messageId);


    /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param messageText, messageId of the message.
     * @return updated all messageText according to message id provided as parameter.
     */
    @Query("UPDATE Message SET messageText = :messageTextVar WHERE messageId = :messageIdVar")
    int updateMessageById(@Param("messageTextVar") String postedBy,  @Param("messageIdVar") String id);


    /** JPQL follows the same structure we have laready used in SQL clauses.
     * @param postedBy of the message.
     * @return all messages with the posted by provided as parameter.
     */
    @Query("FROM Message WHERE postedBy = :posted_byVar")
    List<Message> findByUserId (@Param("posted_byVar") int posted_by);

}
