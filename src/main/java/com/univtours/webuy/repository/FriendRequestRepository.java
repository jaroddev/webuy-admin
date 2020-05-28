package com.univtours.webuy.repository;

import com.univtours.webuy.domain.FriendRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FriendRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("select friendRequest from FriendRequest friendRequest where friendRequest.receiver.login = ?#{principal.username}")
    List<FriendRequest> findByReceiverIsCurrentUser();

    @Query("select friendRequest from FriendRequest friendRequest where friendRequest.sender.login = ?#{principal.username}")
    List<FriendRequest> findBySenderIsCurrentUser();
}
