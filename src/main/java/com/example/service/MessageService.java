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
     * In this method, we create new messages. The request body will contain a JSON representation of a message,
     *  which should be persisted to the database, but will not contain a messageId. The creation of the message 
     * will be successful if and only if the messageText is not blank, is not over 255 characters, and postedBy refers 
     * to a real, existing user. 
     * @param message
     * @return persisted message entity.
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

    /**
     * In this method, we delete the existed method according to the message id.The deletion of an existing message
     * should remove an existing message from the database. If the message existed, the response body should contain
     *  the number of rows updated (1).
     * @param messageId
     */

    @Transactional
    public int deleteMessage(Integer messageId) {
        return messageRepository.deleteByMessageId(messageId);
    }

    /**
     * In this method we update the message by the message id. The update of a message should be successful if and only
     *  if the message id already exists and the new messageText is not blank and is not over 255 characters. If the update
     * is successful, the response body should contain the number of rows updated (1), and the response status should be 200, 
     * which is the default. The message existing on the database should have the updated messageText.
     */

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


    /**
     * In this method, we retrieve all message by the user. The response body should contain a JSON representation 
     * of a list containing all messages posted by a particular user, which is retrieved from the database. 
     * @param accountId
     * @return all messages persisted message entity.
     */
    public List<Message> getAllMsgByUser(Integer accountId) {
        return messageRepository.findByUserId(accountId);

    }
}
