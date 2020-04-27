package com.truesoft.construction.service.mapper;

import com.truesoft.construction.domain.Work;
import com.truesoft.construction.service.dto.WorkDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-27T14:09:24+0200",
    comments = "version: 1.3.1.Final, compiler: Eclipse JDT (IDE) 3.19.0.v20190903-0936, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class WorkMapperImpl implements WorkMapper {

    @Override
    public WorkDTO toDto(Work arg0) {
        if ( arg0 == null ) {
            return null;
        }

        WorkDTO workDTO = new WorkDTO();

        workDTO.setId( arg0.getId() );
        workDTO.setDescription( arg0.getDescription() );
        workDTO.setDateCreated( arg0.getDateCreated() );

        return workDTO;
    }

    @Override
    public List<WorkDTO> toDto(List<Work> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<WorkDTO> list = new ArrayList<WorkDTO>( arg0.size() );
        for ( Work work : arg0 ) {
            list.add( toDto( work ) );
        }

        return list;
    }

    @Override
    public Work toEntity(WorkDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Work work = new Work();

        work.setDateCreated( arg0.getDateCreated() );
        work.setDescription( arg0.getDescription() );
        work.setId( arg0.getId() );

        return work;
    }

    @Override
    public List<Work> toEntity(List<WorkDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Work> list = new ArrayList<Work>( arg0.size() );
        for ( WorkDTO workDTO : arg0 ) {
            list.add( toEntity( workDTO ) );
        }

        return list;
    }
}
