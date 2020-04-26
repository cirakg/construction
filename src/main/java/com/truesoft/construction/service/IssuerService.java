package com.truesoft.construction.service;

import com.truesoft.construction.domain.Issuer;
import com.truesoft.construction.repository.IssuerRepository;
import com.truesoft.construction.service.dto.IssuerDTO;
import com.truesoft.construction.service.mapper.IssuerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Issuer}.
 */
@Service
@Transactional
public class IssuerService {

    private final Logger log = LoggerFactory.getLogger(IssuerService.class);

    private final IssuerRepository issuerRepository;

    private final IssuerMapper issuerMapper;

    public IssuerService(IssuerRepository issuerRepository, IssuerMapper issuerMapper) {
        this.issuerRepository = issuerRepository;
        this.issuerMapper = issuerMapper;
    }

    /**
     * Save a issuer.
     *
     * @param issuerDTO the entity to save.
     * @return the persisted entity.
     */
    public IssuerDTO save(IssuerDTO issuerDTO) {
        log.debug("Request to save Issuer : {}", issuerDTO);
        Issuer issuer = issuerMapper.toEntity(issuerDTO);
        issuer = issuerRepository.save(issuer);
        return issuerMapper.toDto(issuer);
    }

    /**
     * Get all the issuers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IssuerDTO> findAll() {
        log.debug("Request to get all Issuers");
        return issuerRepository.findAll().stream()
            .map(issuerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one issuer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IssuerDTO> findOne(Long id) {
        log.debug("Request to get Issuer : {}", id);
        return issuerRepository.findById(id)
            .map(issuerMapper::toDto);
    }

    /**
     * Delete the issuer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Issuer : {}", id);
        issuerRepository.deleteById(id);
    }
}
