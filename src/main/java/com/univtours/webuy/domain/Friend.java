package com.univtours.webuy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Friend.
 */
@Entity
@Table(name = "friend")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "friends", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "friends", allowSetters = true)
    private User friend;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Friend user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public Friend friend(User user) {
        this.friend = user;
        return this;
    }

    public void setFriend(User user) {
        this.friend = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friend)) {
            return false;
        }
        return id != null && id.equals(((Friend) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Friend{" +
            "id=" + getId() +
            "}";
    }
}
