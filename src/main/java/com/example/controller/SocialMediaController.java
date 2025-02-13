package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    // Service classes
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /***
     * Calls the registerUser method from the account service class to register a user. Spring Boot handles status exceptions
     * thrown when the username is a duplicate. If the account is not null, pass the user into the response body and set the
     * status to 200 
     * @param account
     * @return the account as a ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account registeredAccount = this.accountService.registerUser(account);
        if(registeredAccount != null) { // check if the registeredAccount is null
            return ResponseEntity.status(200).body(registeredAccount);
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    /***
     * Calls the loginUser method from the account service class to log in a user. Spring Boot handles status exceptions
     * thrown when the credentials are invalid or when the credentials are null. If no errors occur, pass the logged in user
     * to the response entity with status 200
     * @param account
     * @return the account as a ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        Account loggedInAccount = this.accountService.loginUser(account.getUsername(), account.getPassword());
        return ResponseEntity.status(200).body(loggedInAccount);
    }

    /***
     * Calls the createMessage method from the message service class to create a message. Spring Boot handles status exceptions
     * thrown when the message is null, not created by a valid user, too long, or blank. If no errors occur, pass the created 
     * message to the response entity with status 200
     * @param message
     * @return the message as a ResponseEntity
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = this.messageService.createMessage(message);
        return ResponseEntity.status(200).body(createdMessage);
    }

    /***
     * Calls the getAllMessages method from the message service class to get all messages. Status will always be 200, even if
     * the getAllMessages returns null. Passes the retrieved messages to the response entity with status 200.
     * @return the list of messages as a ResponseEntity
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(this.messageService.getAllMessages());
    }

    /***
     * Calls the getMessageById method from the message service class to get a message using its id. Status will always be 200
     * even if the getMessageById returns null. Passes the retrieved message to the response entity with status 200.
     * @param message_id
     * @return the message as a ResponseEntity
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessage(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(this.messageService.getMessageById(message_id));
    }

    /***
     * Calls the deleteMessage method from the message service class to delete a message using its id. Status will always be 200
     * even if the deleteMessage returns null. Passes the number of rows affected to the response entity with status 200.
     * @param message_id
     * @return the number of rows affected as a ResponseEntity
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(this.messageService.deleteMessage(message_id));
    }

    /***
     * Calls the updateMessage method from the message service class to update an existing message. Spring Boot handles 
     * status exceptions thrown when the input message is null, message to update is not found, new message text is too long, 
     * or the new message text is blank. If no errors occur, pass the updated message to the response entity with status 200
     * @param message_id the id number of the message to be updated
     * @param msg the message object containing the new message text
     * @return the number of rows afftected as a ResponseEntity
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message msg) {
        Integer updatedRows = this.messageService.updateMessage(message_id, msg);
        return ResponseEntity.status(200).body(updatedRows);
    }

    /***
     * Calls the getMessageByUser method from the message service class to get a message using the user's account_id. Status 
     * will always be 200 even if the getMessageByUser returns null. Passes the retrieved message to the response entity with 
     * status 200.
     * @param account_id
     * @return the message as a ResponseEntity
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer account_id) {
        return ResponseEntity.status(200).body(this.messageService.getMessagesByUser(account_id));
    }
}