package com.truesoft.construction.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class ConstructionSiteWorkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstructionSiteWork.class);
        ConstructionSiteWork constructionSiteWork1 = new ConstructionSiteWork();
        constructionSiteWork1.setId(1L);
        ConstructionSiteWork constructionSiteWork2 = new ConstructionSiteWork();
        constructionSiteWork2.setId(constructionSiteWork1.getId());
        assertThat(constructionSiteWork1).isEqualTo(constructionSiteWork2);
        constructionSiteWork2.setId(2L);
        assertThat(constructionSiteWork1).isNotEqualTo(constructionSiteWork2);
        constructionSiteWork1.setId(null);
        assertThat(constructionSiteWork1).isNotEqualTo(constructionSiteWork2);
    }
}
