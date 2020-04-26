package com.truesoft.construction.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class ConstructionSiteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstructionSiteDTO.class);
        ConstructionSiteDTO constructionSiteDTO1 = new ConstructionSiteDTO();
        constructionSiteDTO1.setId(1L);
        ConstructionSiteDTO constructionSiteDTO2 = new ConstructionSiteDTO();
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
        constructionSiteDTO2.setId(constructionSiteDTO1.getId());
        assertThat(constructionSiteDTO1).isEqualTo(constructionSiteDTO2);
        constructionSiteDTO2.setId(2L);
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
        constructionSiteDTO1.setId(null);
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
    }
}
