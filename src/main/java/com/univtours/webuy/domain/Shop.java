package com.univtours.webuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Shop.
 */
@Entity
@Table(name = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shop")
    private Set<Offer> offers = new HashSet<>();

    @OneToMany(mappedBy = "shop")
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "shops", allowSetters = true)
    private ShopGroup shopGroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "shops", allowSetters = true)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public Shop offers(Set<Offer> offers) {
        this.offers = offers;
        return this;
    }

    public Shop addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setShop(this);
        return this;
    }

    public Shop removeOffer(Offer offer) {
        this.offers.remove(offer);
        offer.setShop(null);
        return this;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Shop products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Shop addProducts(Product product) {
        this.products.add(product);
        product.setShop(this);
        return this;
    }

    public Shop removeProducts(Product product) {
        this.products.remove(product);
        product.setShop(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public ShopGroup getShopGroup() {
        return shopGroup;
    }

    public Shop shopGroup(ShopGroup shopGroup) {
        this.shopGroup = shopGroup;
        return this;
    }

    public void setShopGroup(ShopGroup shopGroup) {
        this.shopGroup = shopGroup;
    }

    public Address getAddress() {
        return address;
    }

    public Shop address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shop)) {
            return false;
        }
        return id != null && id.equals(((Shop) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shop{" +
            "id=" + getId() +
            "}";
    }
}
