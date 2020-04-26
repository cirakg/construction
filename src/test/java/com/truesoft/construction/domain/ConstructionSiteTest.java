package com.truesoft.construction.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class ConstructionSiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstructionSite.class);
        ConstructionSite constructionSite1 = new ConstructionSite();
        constructionSite1.setId(1L);
        ConstructionSite constructionSite2 = new ConstructionSite();
        constructionSite2.setId(constructionSite1.getId());
        assertThat(constructionSite1).isEqualTo(constructionSite2);
        constructionSite2.setId(2L);
        assertThat(constructionSite1).isNotEqualTo(constructionSite2);
        constructionSite1.setId(null);
        assertThat(constructionSite1).isNotEqualTo(constructionSite2);
    }
}
