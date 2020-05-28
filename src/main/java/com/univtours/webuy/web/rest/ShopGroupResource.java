package com.univtours.webuy.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.univtours.webuy.domain.ShopGroup;
import com.univtours.webuy.repository.ShopGroupRepository;
import com.univtours.webuy.security.AuthoritiesConstants;
import com.univtours.webuy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.univtours.webuy.domain.ShopGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShopGroupResource {

    private final Logger log = LoggerFactory.getLogger(ShopGroupResource.class);

    private static final String ENTITY_NAME = "shopGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShopGroupRepository shopGroupRepository;

    public ShopGroupResource(ShopGroupRepository shopGroupRepository) {
        this.shopGroupRepository = shopGroupRepository;
    }

    /**
     * {@code POST  /shop-groups} : Create a new shopGroup.
     *
     * @param shopGroup the shopGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shopGroup, or with status {@code 400 (Bad Request)} if the shopGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shop-groups")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ShopGroup> createShopGroup(@RequestPart ShopGroup shopGroup, @RequestPart byte[] logo) throws URISyntaxException {
        log.debug("REST request to save ShopGroup : {}", shopGroup);
        if (shopGroup.getId() != null) {
            throw new BadRequestAlertException("A new shopGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        if(logo == null) {
        	throw new BadRequestAlertException("Problem with image handling", ENTITY_NAME, "no logo");
        }
        
        ShopGroup result = shopGroupRepository.save(shopGroup);
        return ResponseEntity.created(new URI("/api/shop-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shop-groups} : Updates an existing shopGroup.
     *
     * @param shopGroup the shopGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shopGroup,
     * or with status {@code 400 (Bad Request)} if the shopGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shopGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shop-groups")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ShopGroup> updateShopGroup(@RequestBody ShopGroup shopGroup) throws URISyntaxException {
        log.debug("REST request to update ShopGroup : {}", shopGroup);
        if (shopGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShopGroup result = shopGroupRepository.save(shopGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shopGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shop-groups} : get all the shopGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shopGroups in body.
     */
    @GetMapping("/shop-groups")
    public List<ShopGroup> getAllShopGroups() {
        log.debug("REST request to get all ShopGroups");
        return shopGroupRepository.findAll();
    }

    /**
     * {@code GET  /shop-groups/:id} : get the "id" shopGroup.
     *
     * @param id the id of the shopGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shopGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shop-groups/{id}")
    public ResponseEntity<ShopGroup> getShopGroup(@PathVariable Long id) {
        log.debug("REST request to get ShopGroup : {}", id);
        Optional<ShopGroup> shopGroup = shopGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shopGroup);
    }

    /**
     * {@code DELETE  /shop-groups/:id} : delete the "id" shopGroup.
     *
     * @param id the id of the shopGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shop-groups/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteShopGroup(@PathVariable Long id) {
        log.debug("REST request to delete ShopGroup : {}", id);

        shopGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
