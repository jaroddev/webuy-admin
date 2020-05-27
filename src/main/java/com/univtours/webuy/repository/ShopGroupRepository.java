package com.univtours.webuy.repository;

import com.univtours.webuy.domain.ShopGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShopGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopGroupRepository extends JpaRepository<ShopGroup, Long> {
}
