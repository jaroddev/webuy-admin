package com.univtours.webuy.web.rest;

import com.univtours.webuy.WeBuyApp;
import com.univtours.webuy.domain.GroupInvitation;
import com.univtours.webuy.repository.GroupInvitationRepository;

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
 * Integration tests for the {@link GroupInvitationResource} REST controller.
 */
@SpringBootTest(classes = WeBuyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupInvitationResourceIT {

    @Autowired
    private GroupInvitationRepository groupInvitationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupInvitationMockMvc;

    private GroupInvitation groupInvitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupInvitation createEntity(EntityManager em) {
        GroupInvitation groupInvitation = new GroupInvitation();
        return groupInvitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupInvitation createUpdatedEntity(EntityManager em) {
        GroupInvitation groupInvitation = new GroupInvitation();
        return groupInvitation;
    }

    @BeforeEach
    public void initTest() {
        groupInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupInvitation() throws Exception {
        int databaseSizeBeforeCreate = groupInvitationRepository.findAll().size();
        // Create the GroupInvitation
        restGroupInvitationMockMvc.perform(post("/api/group-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupInvitation)))
            .andExpect(status().isCreated());

        // Validate the GroupInvitation in the database
        List<GroupInvitation> groupInvitationList = groupInvitationRepository.findAll();
        assertThat(groupInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        GroupInvitation testGroupInvitation = groupInvitationList.get(groupInvitationList.size() - 1);
    }

    @Test
    @Transactional
    public void createGroupInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupInvitationRepository.findAll().size();

        // Create the GroupInvitation with an existing ID
        groupInvitation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupInvitationMockMvc.perform(post("/api/group-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupInvitation)))
            .andExpect(status().isBadRequest());

        // Validate the GroupInvitation in the database
        List<GroupInvitation> groupInvitationList = groupInvitationRepository.findAll();
        assertThat(groupInvitationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupInvitations() throws Exception {
        // Initialize the database
        groupInvitationRepository.saveAndFlush(groupInvitation);

        // Get all the groupInvitationList
        restGroupInvitationMockMvc.perform(get("/api/group-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupInvitation.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getGroupInvitation() throws Exception {
        // Initialize the database
        groupInvitationRepository.saveAndFlush(groupInvitation);

        // Get the groupInvitation
        restGroupInvitationMockMvc.perform(get("/api/group-invitations/{id}", groupInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupInvitation.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGroupInvitation() throws Exception {
        // Get the groupInvitation
        restGroupInvitationMockMvc.perform(get("/api/group-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupInvitation() throws Exception {
        // Initialize the database
        groupInvitationRepository.saveAndFlush(groupInvitation);

        int databaseSizeBeforeUpdate = groupInvitationRepository.findAll().size();

        // Update the groupInvitation
        GroupInvitation updatedGroupInvitation = groupInvitationRepository.findById(groupInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedGroupInvitation are not directly saved in db
        em.detach(updatedGroupInvitation);

        restGroupInvitationMockMvc.perform(put("/api/group-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupInvitation)))
            .andExpect(status().isOk());

        // Validate the GroupInvitation in the database
        List<GroupInvitation> groupInvitationList = groupInvitationRepository.findAll();
        assertThat(groupInvitationList).hasSize(databaseSizeBeforeUpdate);
        GroupInvitation testGroupInvitation = groupInvitationList.get(groupInvitationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupInvitation() throws Exception {
        int databaseSizeBeforeUpdate = groupInvitationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupInvitationMockMvc.perform(put("/api/group-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupInvitation)))
            .andExpect(status().isBadRequest());

        // Validate the GroupInvitation in the database
        List<GroupInvitation> groupInvitationList = groupInvitationRepository.findAll();
        assertThat(groupInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupInvitation() throws Exception {
        // Initialize the database
        groupInvitationRepository.saveAndFlush(groupInvitation);

        int databaseSizeBeforeDelete = groupInvitationRepository.findAll().size();

        // Delete the groupInvitation
        restGroupInvitationMockMvc.perform(delete("/api/group-invitations/{id}", groupInvitation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupInvitation> groupInvitationList = groupInvitationRepository.findAll();
        assertThat(groupInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
