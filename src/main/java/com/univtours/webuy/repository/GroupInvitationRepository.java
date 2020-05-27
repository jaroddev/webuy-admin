package com.univtours.webuy.repository;

import com.univtours.webuy.domain.GroupInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GroupInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, Long> {
}
