package com.univtours.webuy.repository;

import com.univtours.webuy.domain.Message;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select message from Message message where message.sender.login = ?#{principal.username}")
    List<Message> findBySenderIsCurrentUser();
}
