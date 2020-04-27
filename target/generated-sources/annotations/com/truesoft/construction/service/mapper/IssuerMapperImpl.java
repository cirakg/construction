package com.truesoft.construction.service.mapper;

import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.service.dto.IssuerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-27T14:07:13+0200",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.19.0.v20190903-0936, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class IssuerMapperImpl implements IssuerMapper {

    @Override
    public Issuer toEntity(IssuerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Issuer issuer = new Issuer();

        issuer.setId( dto.getId() );
        issuer.setName( dto.getName() );

        return issuer;
    }

    @Override
    public IssuerDTO toDto(Issuer entity) {
        if ( entity == null ) {
            return null;
        }

        IssuerDTO issuerDTO = new IssuerDTO();

        issuerDTO.setId( entity.getId() );
        issuerDTO.setName( entity.getName() );

        return issuerDTO;
    }

    @Override
    public List<Issuer> toEntity(List<IssuerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Issuer> list = new ArrayList<Issuer>( dtoList.size() );
        for ( IssuerDTO issuerDTO : dtoList ) {
            list.add( toEntity( issuerDTO ) );
        }

        return list;
    }

    @Override
    public List<IssuerDTO> toDto(List<Issuer> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<IssuerDTO> list = new ArrayList<IssuerDTO>( entityList.size() );
        for ( Issuer issuer : entityList ) {
            list.add( toDto( issuer ) );
        }

        return list;
    }
}
