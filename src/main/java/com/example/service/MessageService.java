package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
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

    /**
     * @param message
     * @return
     */
    public Message createMessage(Message message){
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new InvalidMessageException("Message text cannot be blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message text cannot exceed 255 characters");
        }
        if (!AccountRepository.findById(message.getPostedBy())) {
            throw new InvalidMessageException("Posted bust reference an existing user");
        }

        message.setMessageId(UUID.randomUUID().toString());
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
