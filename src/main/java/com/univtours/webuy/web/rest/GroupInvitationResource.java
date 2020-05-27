package com.univtours.webuy.web.rest;

import com.univtours.webuy.domain.GroupInvitation;
import com.univtours.webuy.repository.GroupInvitationRepository;
import com.univtours.webuy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.univtours.webuy.domain.GroupInvitation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupInvitationResource {

    private final Logger log = LoggerFactory.getLogger(GroupInvitationResource.class);

    private static final String ENTITY_NAME = "groupInvitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupInvitationRepository groupInvitationRepository;

    public GroupInvitationResource(GroupInvitationRepository groupInvitationRepository) {
        this.groupInvitationRepository = groupInvitationRepository;
    }

    /**
     * {@code POST  /group-invitations} : Create a new groupInvitation.
     *
     * @param groupInvitation the groupInvitation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupInvitation, or with status {@code 400 (Bad Request)} if the groupInvitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-invitations")
    public ResponseEntity<GroupInvitation> createGroupInvitation(@RequestBody GroupInvitation groupInvitation) throws URISyntaxException {
        log.debug("REST request to save GroupInvitation : {}", groupInvitation);
        if (groupInvitation.getId() != null) {
            throw new BadRequestAlertException("A new groupInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupInvitation result = groupInvitationRepository.save(groupInvitation);
        return ResponseEntity.created(new URI("/api/group-invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-invitations} : Updates an existing groupInvitation.
     *
     * @param groupInvitation the groupInvitation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupInvitation,
     * or with status {@code 400 (Bad Request)} if the groupInvitation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupInvitation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-invitations")
    public ResponseEntity<GroupInvitation> updateGroupInvitation(@RequestBody GroupInvitation groupInvitation) throws URISyntaxException {
        log.debug("REST request to update GroupInvitation : {}", groupInvitation);
        if (groupInvitation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupInvitation result = groupInvitationRepository.save(groupInvitation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupInvitation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-invitations} : get all the groupInvitations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupInvitations in body.
     */
    @GetMapping("/group-invitations")
    public List<GroupInvitation> getAllGroupInvitations() {
        log.debug("REST request to get all GroupInvitations");
        return groupInvitationRepository.findAll();
    }

    /**
     * {@code GET  /group-invitations/:id} : get the "id" groupInvitation.
     *
     * @param id the id of the groupInvitation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupInvitation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-invitations/{id}")
    public ResponseEntity<GroupInvitation> getGroupInvitation(@PathVariable Long id) {
        log.debug("REST request to get GroupInvitation : {}", id);
        Optional<GroupInvitation> groupInvitation = groupInvitationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupInvitation);
    }

    /**
     * {@code DELETE  /group-invitations/:id} : delete the "id" groupInvitation.
     *
     * @param id the id of the groupInvitation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-invitations/{id}")
    public ResponseEntity<Void> deleteGroupInvitation(@PathVariable Long id) {
        log.debug("REST request to delete GroupInvitation : {}", id);

        groupInvitationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
