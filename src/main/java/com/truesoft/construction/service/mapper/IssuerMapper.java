package com.truesoft.construction.service.mapper;


import com.truesoft.construction.domain.*;
import com.truesoft.construction.service.dto.IssuerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Issuer} and its DTO {@link IssuerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IssuerMapper extends EntityMapper<IssuerDTO, Issuer> {



    default Issuer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Issuer issuer = new Issuer();
        issuer.setId(id);
        return issuer;
    }
}
