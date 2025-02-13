package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /***
     * Retrieves all messages from a specified user account using the its id.
     * @param id
     * @return list of messages posted by the user account
     */
    List<Message> findAllByPostedBy(Integer id);
}
