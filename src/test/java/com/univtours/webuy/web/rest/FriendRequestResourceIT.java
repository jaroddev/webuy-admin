package com.univtours.webuy.web.rest;

import com.univtours.webuy.WeBuyApp;
import com.univtours.webuy.domain.FriendRequest;
import com.univtours.webuy.repository.FriendRequestRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FriendRequestResource} REST controller.
 */
@SpringBootTest(classes = WeBuyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FriendRequestResourceIT {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendRequestMockMvc;

    private FriendRequest friendRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest();
        return friendRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FriendRequest createUpdatedEntity(EntityManager em) {
        FriendRequest friendRequest = new FriendRequest();
        return friendRequest;
    }

    @BeforeEach
    public void initTest() {
        friendRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createFriendRequest() throws Exception {
        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();
        // Create the FriendRequest
        restFriendRequestMockMvc.perform(post("/api/friend-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isCreated());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate + 1);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void createFriendRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = friendRequestRepository.findAll().size();

        // Create the FriendRequest with an existing ID
        friendRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendRequestMockMvc.perform(post("/api/friend-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFriendRequests() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get all the friendRequestList
        restFriendRequestMockMvc.perform(get("/api/friend-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friendRequest.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        // Get the friendRequest
        restFriendRequestMockMvc.perform(get("/api/friend-requests/{id}", friendRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friendRequest.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFriendRequest() throws Exception {
        // Get the friendRequest
        restFriendRequestMockMvc.perform(get("/api/friend-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // Update the friendRequest
        FriendRequest updatedFriendRequest = friendRequestRepository.findById(friendRequest.getId()).get();
        // Disconnect from session so that the updates on updatedFriendRequest are not directly saved in db
        em.detach(updatedFriendRequest);

        restFriendRequestMockMvc.perform(put("/api/friend-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFriendRequest)))
            .andExpect(status().isOk());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
        FriendRequest testFriendRequest = friendRequestList.get(friendRequestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFriendRequest() throws Exception {
        int databaseSizeBeforeUpdate = friendRequestRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendRequestMockMvc.perform(put("/api/friend-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(friendRequest)))
            .andExpect(status().isBadRequest());

        // Validate the FriendRequest in the database
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFriendRequest() throws Exception {
        // Initialize the database
        friendRequestRepository.saveAndFlush(friendRequest);

        int databaseSizeBeforeDelete = friendRequestRepository.findAll().size();

        // Delete the friendRequest
        restFriendRequestMockMvc.perform(delete("/api/friend-requests/{id}", friendRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll();
        assertThat(friendRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
