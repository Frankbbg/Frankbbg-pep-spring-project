package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.MessageNotFoundException;
import com.example.exception.MessageValidationException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    // JPA data repositories
    public MessageRepository messageRepository;
    public AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /***
     * Creates a message by persisting the input message to the database. This method will throw an exception if any of 
     * these failure cases occur: msg is null, message was not posted by a valid user, message text is blank,
     * the message is too long
     * @param msg
     * @return the persisted message
     */
    public Message createMessage(Message msg) {
        if(msg == null) // make sure the message isnt null
            throw new MessageNotFoundException("message cannot be null");

        // check if the message was posted by a valid user
        if(!this.accountRepository.findById(msg.getPostedBy()).isPresent())
            throw new MessageValidationException("Message not posted by valid user");

        // check if the message text is blank
        if(msg.getMessageText().isBlank())
            throw new MessageValidationException("Message cannot be blank");

        // check if the message length is over 255 characters
        if(msg.getMessageText().length() > 255)
            throw new MessageValidationException("Message too long. Message should be under 255 characters");
        
        return this.messageRepository.save(msg); // persist the message to the database
    }

    /***
     * Retrieves all messages in the database using JPARepository's findAll method
     * @return a list of all messages in the database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /***
     * Retrieves a message from the database using the message id. Returns null if the message doesn't exist in the database.
     * @param id
     * @return a single message
     */
    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null); // return the found message, otherwise return null
    }

    /***
     * Removes a message from the database using it's id. Returns null if the message is not present in the database.
     * @param id
     * @return the number of rows effected (always 1)
     */
    public Integer deleteMessage(Integer id) {
        if(this.messageRepository.findById(id).isPresent()) { // check if the message exists
            messageRepository.deleteById(id);
            return 1; // Return rows updated (it will always be 1 since we only delete one message at a time)
        } else {
            return null;
        }
    }

    /***
     * Updates a message by changing it's message text and persisting the updated message to the database. This method 
     * will throw an exception if any of these failure cases occur: msg is null, message to update is null, 
     * message text is blank, the message text is too long
     * @param msg_id the id of the message to update
     * @param msg the message object containing the new message text
     * @return the number of rows affected (always 1)
     */
    public Integer updateMessage(int msg_id, Message msg) {
        if(msg == null) // check if the initial msg is null
            throw new MessageNotFoundException("Message and messsage text cannot be null");

        // grab the message to update using the id
        Message updatedMessage = this.messageRepository.findById(msg_id).orElse(null);

        if(updatedMessage == null) // check if the message to update is null
            throw new MessageNotFoundException("Message Not Found");

        if(msg.getMessageText().isBlank()) // check if the message text is blank
            throw new MessageValidationException("Message cannot be blank");
        
        if(msg.getMessageText().length() > 255) // check if the length is over 255 characters
            throw new MessageValidationException("Message too long. Message should be under 255 characters");

        updatedMessage.setMessageText(msg.getMessageText()); //set the message text of the message to update
        this.messageRepository.save(updatedMessage); // persist the message to the database
        return 1; // always return 1 since we always only modify one row at a time
    }

    /***
     * Retrives all messages sent by a specific user from the database. If no message is found, returns null.
     * @param userId
     * @return the list of messages sent by the user
     */
    public List<Message> getMessagesByUser(Integer userId) {
        return messageRepository.findAllByPostedBy(userId);
    }
}