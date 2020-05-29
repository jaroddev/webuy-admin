package com.univtours.webuy.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "group")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "jhi_groups_member",
               joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
    private Set<User> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public Group messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public Group addMessages(Message message) {
        this.messages.add(message);
        message.setGroup(this);
        return this;
    }

    public Group removeMessages(Message message) {
        this.messages.remove(message);
        message.setGroup(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<User> getMembers() {
        return members;
    }

    public Group members(Set<User> users) {
        this.members = users;
        return this;
    }

    public Group addMember(User user) {
        this.members.add(user);
        return this;
    }

    public Group removeMember(User user) {
        this.members.remove(user);
        return this;
    }

    public void setMembers(Set<User> users) {
        this.members = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            "}";
    }
}
