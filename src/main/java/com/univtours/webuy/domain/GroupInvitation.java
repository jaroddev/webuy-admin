package com.univtours.webuy.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GroupInvitation.
 */
@Entity
@Table(name = "groupinvitations")
public class GroupInvitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupInvitation)) {
            return false;
        }
        return id != null && id.equals(((GroupInvitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupInvitation{" +
            "id=" + getId() +
            "}";
    }
}
