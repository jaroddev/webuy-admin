package com.univtours.webuy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.univtours.webuy.web.rest.TestUtil;

public class GroupInvitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupInvitation.class);
        GroupInvitation groupInvitation1 = new GroupInvitation();
        groupInvitation1.setId(1L);
        GroupInvitation groupInvitation2 = new GroupInvitation();
        groupInvitation2.setId(groupInvitation1.getId());
        assertThat(groupInvitation1).isEqualTo(groupInvitation2);
        groupInvitation2.setId(2L);
        assertThat(groupInvitation1).isNotEqualTo(groupInvitation2);
        groupInvitation1.setId(null);
        assertThat(groupInvitation1).isNotEqualTo(groupInvitation2);
    }
}
