package com.example.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.example.Repository.MessageRepository;
import com.example.entity.Account;
import com.example.entity.Message;
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
    public Account createAccount(@RequestBody Account user){
        return accountService.createAccount(user);
    }

    @PostMapping
    public Account loginAccount(@RequestBody Account user){
        return accountService.loginAccount(user);
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message){
        return messageService.createMessage(message);
    }

    @GetMapping
    public List<Message> retrieveAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id){
        Optional<Message> user = messageService.getMessageById(id);
        return user.map(ResponseEntity::ok)
                    .orElseGet(() -> MessageRepository.notFound().build(200));

    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id) {
        messageService.deleteMessage(id);
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Message> updatedMsgById(@PathVariable Integer id){
        Optional<Message> message = messageService.updateMessageById(id);
    } 





}
