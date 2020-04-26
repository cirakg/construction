package com.truesoft.construction.service.mapper;

import com.truesoft.construction.domain.Work;
import com.truesoft.construction.service.dto.WorkDTO;
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
public class WorkMapperImpl implements WorkMapper {

    @Override
    public Work toEntity(WorkDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Work work = new Work();

        work.setId( dto.getId() );
        work.setDescription( dto.getDescription() );
        work.setDateCreated( dto.getDateCreated() );

        return work;
    }

    @Override
    public WorkDTO toDto(Work entity) {
        if ( entity == null ) {
            return null;
        }

        WorkDTO workDTO = new WorkDTO();

        workDTO.setId( entity.getId() );
        workDTO.setDescription( entity.getDescription() );
        workDTO.setDateCreated( entity.getDateCreated() );

        return workDTO;
    }

    @Override
    public List<Work> toEntity(List<WorkDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Work> list = new ArrayList<Work>( dtoList.size() );
        for ( WorkDTO workDTO : dtoList ) {
            list.add( toEntity( workDTO ) );
        }

        return list;
    }

    @Override
    public List<WorkDTO> toDto(List<Work> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<WorkDTO> list = new ArrayList<WorkDTO>( entityList.size() );
        for ( Work work : entityList ) {
            list.add( toDto( work ) );
        }

        return list;
    }
}
