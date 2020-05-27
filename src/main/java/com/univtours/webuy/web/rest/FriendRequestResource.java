package com.univtours.webuy.web.rest;

import com.univtours.webuy.domain.FriendRequest;
import com.univtours.webuy.repository.FriendRequestRepository;
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
 * REST controller for managing {@link com.univtours.webuy.domain.FriendRequest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FriendRequestResource {

    private final Logger log = LoggerFactory.getLogger(FriendRequestResource.class);

    private static final String ENTITY_NAME = "friendRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestResource(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
     * {@code POST  /friend-requests} : Create a new friendRequest.
     *
     * @param friendRequest the friendRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new friendRequest, or with status {@code 400 (Bad Request)} if the friendRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/friend-requests")
    public ResponseEntity<FriendRequest> createFriendRequest(@RequestBody FriendRequest friendRequest) throws URISyntaxException {
        log.debug("REST request to save FriendRequest : {}", friendRequest);
        if (friendRequest.getId() != null) {
            throw new BadRequestAlertException("A new friendRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FriendRequest result = friendRequestRepository.save(friendRequest);
        return ResponseEntity.created(new URI("/api/friend-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /friend-requests} : Updates an existing friendRequest.
     *
     * @param friendRequest the friendRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated friendRequest,
     * or with status {@code 400 (Bad Request)} if the friendRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the friendRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/friend-requests")
    public ResponseEntity<FriendRequest> updateFriendRequest(@RequestBody FriendRequest friendRequest) throws URISyntaxException {
        log.debug("REST request to update FriendRequest : {}", friendRequest);
        if (friendRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FriendRequest result = friendRequestRepository.save(friendRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, friendRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /friend-requests} : get all the friendRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of friendRequests in body.
     */
    @GetMapping("/friend-requests")
    public List<FriendRequest> getAllFriendRequests() {
        log.debug("REST request to get all FriendRequests");
        return friendRequestRepository.findAll();
    }

    /**
     * {@code GET  /friend-requests/:id} : get the "id" friendRequest.
     *
     * @param id the id of the friendRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the friendRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/friend-requests/{id}")
    public ResponseEntity<FriendRequest> getFriendRequest(@PathVariable Long id) {
        log.debug("REST request to get FriendRequest : {}", id);
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(friendRequest);
    }

    /**
     * {@code DELETE  /friend-requests/:id} : delete the "id" friendRequest.
     *
     * @param id the id of the friendRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/friend-requests/{id}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable Long id) {
        log.debug("REST request to delete FriendRequest : {}", id);

        friendRequestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
