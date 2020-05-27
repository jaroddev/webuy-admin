package com.univtours.webuy.repository;

import com.univtours.webuy.domain.FriendRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FriendRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
