package com.truesoft.construction.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssuerMapperTest {

    private IssuerMapper issuerMapper;

    @BeforeEach
    public void setUp() {
        issuerMapper = new IssuerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issuerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issuerMapper.fromId(null)).isNull();
    }
}
