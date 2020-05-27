package com.univtours.webuy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.univtours.webuy.web.rest.TestUtil;

public class ShopGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopGroup.class);
        ShopGroup shopGroup1 = new ShopGroup();
        shopGroup1.setId(1L);
        ShopGroup shopGroup2 = new ShopGroup();
        shopGroup2.setId(shopGroup1.getId());
        assertThat(shopGroup1).isEqualTo(shopGroup2);
        shopGroup2.setId(2L);
        assertThat(shopGroup1).isNotEqualTo(shopGroup2);
        shopGroup1.setId(null);
        assertThat(shopGroup1).isNotEqualTo(shopGroup2);
    }
}
