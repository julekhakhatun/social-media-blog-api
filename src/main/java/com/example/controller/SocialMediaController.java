package com.example.controller;

import java.io.InvalidClassException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account user){
        try {
            Account createdAccount = accountService.createAccount(user);
            return ResponseEntity.ok(createdAccount);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (InvalidClassException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @PostMapping
    public ResponseEntity<?> loginAccount(@RequestBody Account account){
        try {
            Account loggedInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loggedInAccount);
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
       
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (InvalidMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       
    }

    @GetMapping
    public ResponseEntity<List<Message>> retrieveAllMessages(){
        final List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id){
        Optional<Message> user = messageService.getMessageById(id);
        return user.map(ResponseEntity::ok)
                    .orElseGet(() -> MessageRepository.notFound().build(200));

    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer id) {
        int deletedRows = messageService.deleteMessage(id);
        if (deletedRows > 0) {
            return ResponseEntity.ok(deletedRows);
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<?> updatedMsgById(@PathVariable Integer id, @RequestBody Map<Integer, String> updates) {
        try {
            int updatedRows = messageService.updateMessageById(id, updates.get("messageText"));
            return ResponseEntity.ok(updatedRows);
        } catch (InvalidClassException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       
    }
    
    @GetMapping("/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer account_id){
        List<Message> messages = (List<Message>) messageService.getAllMsgByUser(account_id);
        return ResponseEntity.ok(messages);
    }





}
