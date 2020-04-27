package com.truesoft.construction.service.mapper;

import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.service.dto.ConstructionSiteDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-27T14:14:20+0200",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.19.0.v20190903-0936, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class ConstructionSiteMapperImpl implements ConstructionSiteMapper {

    @Override
    public ConstructionSiteDTO toDto(ConstructionSite arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ConstructionSiteDTO constructionSiteDTO = new ConstructionSiteDTO();

        constructionSiteDTO.setId( arg0.getId() );
        constructionSiteDTO.setName( arg0.getName() );
        constructionSiteDTO.setDescription( arg0.getDescription() );

        return constructionSiteDTO;
    }

    @Override
    public List<ConstructionSiteDTO> toDto(List<ConstructionSite> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ConstructionSiteDTO> list = new ArrayList<ConstructionSiteDTO>( arg0.size() );
        for ( ConstructionSite constructionSite : arg0 ) {
            list.add( toDto( constructionSite ) );
        }

        return list;
    }

    @Override
    public ConstructionSite toEntity(ConstructionSiteDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ConstructionSite constructionSite = new ConstructionSite();

        constructionSite.setDescription( arg0.getDescription() );
        constructionSite.setName( arg0.getName() );
        constructionSite.setId( arg0.getId() );

        return constructionSite;
    }

    @Override
    public List<ConstructionSite> toEntity(List<ConstructionSiteDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ConstructionSite> list = new ArrayList<ConstructionSite>( arg0.size() );
        for ( ConstructionSiteDTO constructionSiteDTO : arg0 ) {
            list.add( toEntity( constructionSiteDTO ) );
        }

        return list;
    }
}
