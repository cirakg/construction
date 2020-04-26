package com.truesoft.construction.service.mapper;

import org.mapstruct.Mapper;

import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.service.dto.ConstructionSiteDTO;

/**
 * Mapper for the entity {@link ConstructionSite} and its DTO
 * {@link ConstructionSiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConstructionSiteMapper extends EntityMapper<ConstructionSiteDTO, ConstructionSite> {

	default ConstructionSite fromId(Long id) {
		if (id == null) {
			return null;
		}
		ConstructionSite constructionSite = new ConstructionSite();
		constructionSite.setId(id);
		return constructionSite;
	}
}
