package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAllMessages();
    }

    public Optional<Message> getMessageById(Integer id){
        return messageRepository.findById(id);
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public void deleteMessage (Integer id){
        messageRepository.deleteByMessageId(id);
    }

    public Message updateMessageById (Integer id){
        messageRepository.updateMessageById(id);
        return null;
    }

    public ResponseEntity<Message> getAllMsgByUser(Integer acc_id){
        messageRepository.findByUserName(acc_id);
        return null;
    }
}
