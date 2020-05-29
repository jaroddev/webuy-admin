package com.univtours.webuy.web.rest;

import com.univtours.webuy.WeBuyApp;
import com.univtours.webuy.domain.ShopGroup;
import com.univtours.webuy.repository.ShopGroupRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShopGroupResource} REST controller.
 */
@SpringBootTest(classes = WeBuyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShopGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private ShopGroupRepository shopGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShopGroupMockMvc;

    private ShopGroup shopGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopGroup createEntity(EntityManager em) {
        ShopGroup shopGroup = new ShopGroup()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return shopGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopGroup createUpdatedEntity(EntityManager em) {
        ShopGroup shopGroup = new ShopGroup()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        return shopGroup;
    }

    @BeforeEach
    public void initTest() {
        shopGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopGroup() throws Exception {
        int databaseSizeBeforeCreate = shopGroupRepository.findAll().size();
        // Create the ShopGroup
        restShopGroupMockMvc.perform(post("/api/shop-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shopGroup)))
            .andExpect(status().isCreated());

        // Validate the ShopGroup in the database
        List<ShopGroup> shopGroupList = shopGroupRepository.findAll();
        assertThat(shopGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ShopGroup testShopGroup = shopGroupList.get(shopGroupList.size() - 1);
        assertThat(testShopGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShopGroup.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testShopGroup.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createShopGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopGroupRepository.findAll().size();

        // Create the ShopGroup with an existing ID
        shopGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopGroupMockMvc.perform(post("/api/shop-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shopGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ShopGroup in the database
        List<ShopGroup> shopGroupList = shopGroupRepository.findAll();
        assertThat(shopGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShopGroups() throws Exception {
        // Initialize the database
        shopGroupRepository.saveAndFlush(shopGroup);

        // Get all the shopGroupList
        restShopGroupMockMvc.perform(get("/api/shop-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }
    
    @Test
    @Transactional
    public void getShopGroup() throws Exception {
        // Initialize the database
        shopGroupRepository.saveAndFlush(shopGroup);

        // Get the shopGroup
        restShopGroupMockMvc.perform(get("/api/shop-groups/{id}", shopGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shopGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }
    @Test
    @Transactional
    public void getNonExistingShopGroup() throws Exception {
        // Get the shopGroup
        restShopGroupMockMvc.perform(get("/api/shop-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopGroup() throws Exception {
        // Initialize the database
        shopGroupRepository.saveAndFlush(shopGroup);

        int databaseSizeBeforeUpdate = shopGroupRepository.findAll().size();

        // Update the shopGroup
        ShopGroup updatedShopGroup = shopGroupRepository.findById(shopGroup.getId()).get();
        // Disconnect from session so that the updates on updatedShopGroup are not directly saved in db
        em.detach(updatedShopGroup);
        updatedShopGroup
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restShopGroupMockMvc.perform(put("/api/shop-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShopGroup)))
            .andExpect(status().isOk());

        // Validate the ShopGroup in the database
        List<ShopGroup> shopGroupList = shopGroupRepository.findAll();
        assertThat(shopGroupList).hasSize(databaseSizeBeforeUpdate);
        ShopGroup testShopGroup = shopGroupList.get(shopGroupList.size() - 1);
        assertThat(testShopGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShopGroup.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testShopGroup.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingShopGroup() throws Exception {
        int databaseSizeBeforeUpdate = shopGroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShopGroupMockMvc.perform(put("/api/shop-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shopGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ShopGroup in the database
        List<ShopGroup> shopGroupList = shopGroupRepository.findAll();
        assertThat(shopGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShopGroup() throws Exception {
        // Initialize the database
        shopGroupRepository.saveAndFlush(shopGroup);

        int databaseSizeBeforeDelete = shopGroupRepository.findAll().size();

        // Delete the shopGroup
        restShopGroupMockMvc.perform(delete("/api/shop-groups/{id}", shopGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShopGroup> shopGroupList = shopGroupRepository.findAll();
        assertThat(shopGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
