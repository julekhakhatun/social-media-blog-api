package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }

    /**
     * @param message
     * @return
     */
    public Message createMessage(Message message) throws InvalidMessageException {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new InvalidMessageException("Message text cannot be blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message text cannot exceed 255 characters");
        }
        Optional<Account> account = accountRepository.findById(message.getPostedBy());
        if (!account.isPresent()) {
            throw new InvalidMessageException("Posted bust reference an existing user");
        }
        return messageRepository.save(message);
    }

    @Transactional
    public int deleteMessage(Integer messageId) {
        return messageRepository.deleteByMessageId(messageId);
    }

    public int updateMessageById(Integer id, Message message) throws InvalidMessageException {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new InvalidMessageException("Message text cannot be blank");
        }
        if (message.getMessageText().length() > 255) {
            throw new InvalidMessageException("Message text cannot exceed 255 characters");
        }

        Message existingMessage = messageRepository.findById(id)
                .orElseThrow(() -> new InvalidMessageException("Message with ID " + id + "  not found"));

        existingMessage.setMessageText(message.getMessageText());
        messageRepository.save(existingMessage);
        return 1;
    }

    public List<Message> getAllMsgByUser(Integer accountId) {
        return messageRepository.findByUserId(accountId);

    }
}
