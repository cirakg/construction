package com.truesoft.construction.service.mapper;

import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.service.dto.ConstructionSiteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-26T21:15:42+0200",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.19.0.v20190903-0936, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class ConstructionSiteMapperImpl implements ConstructionSiteMapper {

    @Override
    public ConstructionSite toEntity(ConstructionSiteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ConstructionSite constructionSite = new ConstructionSite();

        constructionSite.setId( dto.getId() );
        constructionSite.setName( dto.getName() );
        constructionSite.setDescription( dto.getDescription() );

        return constructionSite;
    }

    @Override
    public ConstructionSiteDTO toDto(ConstructionSite entity) {
        if ( entity == null ) {
            return null;
        }

        ConstructionSiteDTO constructionSiteDTO = new ConstructionSiteDTO();

        constructionSiteDTO.setId( entity.getId() );
        constructionSiteDTO.setName( entity.getName() );
        constructionSiteDTO.setDescription( entity.getDescription() );

        return constructionSiteDTO;
    }

    @Override
    public List<ConstructionSite> toEntity(List<ConstructionSiteDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ConstructionSite> list = new ArrayList<ConstructionSite>( dtoList.size() );
        for ( ConstructionSiteDTO constructionSiteDTO : dtoList ) {
            list.add( toEntity( constructionSiteDTO ) );
        }

        return list;
    }

    @Override
    public List<ConstructionSiteDTO> toDto(List<ConstructionSite> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ConstructionSiteDTO> list = new ArrayList<ConstructionSiteDTO>( entityList.size() );
        for ( ConstructionSite constructionSite : entityList ) {
            list.add( toDto( constructionSite ) );
        }

        return list;
    }
}
