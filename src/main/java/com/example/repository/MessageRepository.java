package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>  {

    @Query("DELETE FROM Message WHERE messageId = :messageIdVar")
    List<Message> deleteByMessageId (@Param("messageIdVar") int messageId);

    
    @Query("FROM Message WHERE messageId = :messageIdVar")
    List<Account> findById (@Param("messageIdVar") int messageId);


    @Query("SELECT * FROM Message")
    List<Message> findAllMessages();


    @Query("INSERT INTO Message(messageId, postedBy, messageText, timePostedEpoch) VALUES (:messageIdVar, :postedByVar, :messageTextVar, :timePostedEpochVar)")
    List<Message> findInsertMessages (@Param("messageIdVar") int messageId, @Param("postedByVar") int postedBy, @Param("messageTextVar") String messageText, @Param("timePostedEpochVar") int timePostedEpoch);


    @Query("UPDATE Message SET messageText = :messageTextVar WHERE messageId = :messageIdVar")
    int updateMessageById(Integer id);


    @Query("FROM Message WHERE posted_by = :posted_byVar")
    List<Account> findByUserName (@Param("posted_by") int posted_by);

}
