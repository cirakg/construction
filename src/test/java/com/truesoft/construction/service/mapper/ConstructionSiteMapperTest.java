package com.truesoft.construction.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConstructionSiteMapperTest {

	private ConstructionSiteMapper constructionSiteMapper;

	@BeforeEach
	public void setUp() {
		constructionSiteMapper = new ConstructionSiteMapperImpl();
	}

	@Test
	public void testEntityFromId() {
		Long id = 1L;
		assertThat(constructionSiteMapper.fromId(id).getId()).isEqualTo(id);
		assertThat(constructionSiteMapper.fromId(null)).isNull();
	}
}
