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

    public int deleteMessage (Integer id){
        if (messageRepository.deleteByMessageId(id) != null) {
            return 1;
        }
        return 0;
    }

    public int updateMessageById (Integer id, String newMessageText){
        if (newMessageText == null || newMessageText.isBlank()) {
            throw new InvalidMessgaeException("Message text cannot be blank");
        }
        if(newMessageText.length() > 255) {
            throw new InvalidMessageException ("Message text cannot exceed 255 characters");
        }

        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new InvalidMessgeException("Message with ID " + id + "  not found"));

        existingMessage.setMessageText(newMessageText);
        messageRepository.save(existingMessage);
        return 1;
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<Message> getAllMsgByUser(Integer acc_id){
        return (ResponseEntity<Message>) messageRepository.findByUserName(acc_id);
       
    }
}
