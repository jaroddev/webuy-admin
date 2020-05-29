package com.univtours.webuy.repository;

import com.univtours.webuy.domain.GroupInvitation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GroupInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, Long> {

    @Query("select groupInvitation from GroupInvitation groupInvitation where groupInvitation.inviting.login = ?#{principal.username}")
    List<GroupInvitation> findByInvitingIsCurrentUser();

    @Query("select groupInvitation from GroupInvitation groupInvitation where groupInvitation.invited.login = ?#{principal.username}")
    List<GroupInvitation> findByInvitedIsCurrentUser();
}
