package com.truesoft.construction.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class IssuerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Issuer.class);
        Issuer issuer1 = new Issuer();
        issuer1.setId(1L);
        Issuer issuer2 = new Issuer();
        issuer2.setId(issuer1.getId());
        assertThat(issuer1).isEqualTo(issuer2);
        issuer2.setId(2L);
        assertThat(issuer1).isNotEqualTo(issuer2);
        issuer1.setId(null);
        assertThat(issuer1).isNotEqualTo(issuer2);
    }
}
