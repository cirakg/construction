package com.truesoft.construction.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class IssuerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssuerDTO.class);
        IssuerDTO issuerDTO1 = new IssuerDTO();
        issuerDTO1.setId(1L);
        IssuerDTO issuerDTO2 = new IssuerDTO();
        assertThat(issuerDTO1).isNotEqualTo(issuerDTO2);
        issuerDTO2.setId(issuerDTO1.getId());
        assertThat(issuerDTO1).isEqualTo(issuerDTO2);
        issuerDTO2.setId(2L);
        assertThat(issuerDTO1).isNotEqualTo(issuerDTO2);
        issuerDTO1.setId(null);
        assertThat(issuerDTO1).isNotEqualTo(issuerDTO2);
    }
}
