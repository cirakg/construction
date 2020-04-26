package com.truesoft.construction.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class TenderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tender.class);
        Tender tender1 = new Tender();
        tender1.setId(1L);
        Tender tender2 = new Tender();
        tender2.setId(tender1.getId());
        assertThat(tender1).isEqualTo(tender2);
        tender2.setId(2L);
        assertThat(tender1).isNotEqualTo(tender2);
        tender1.setId(null);
        assertThat(tender1).isNotEqualTo(tender2);
    }
}
