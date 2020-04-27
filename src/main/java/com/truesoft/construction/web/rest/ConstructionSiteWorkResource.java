package com.truesoft.construction.web.rest;

import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.ConstructionSiteRepository;
import com.truesoft.construction.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.truesoft.construction.domain.ConstructionSiteWork}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConstructionSiteWorkResource {

    private final Logger log = LoggerFactory.getLogger(ConstructionSiteWorkResource.class);

    private static final String ENTITY_NAME = "constructionSiteWork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConstructionSiteWorkRepository constructionSiteWorkRepository;

    private final ConstructionSiteRepository constructionSiteRepository;

    public ConstructionSiteWorkResource(ConstructionSiteWorkRepository constructionSiteWorkRepository, ConstructionSiteRepository constructionSiteRepository) {
        this.constructionSiteWorkRepository = constructionSiteWorkRepository;
        this.constructionSiteRepository = constructionSiteRepository;
    }

    /**
     * {@code POST  /construction-site-works} : Create a new constructionSiteWork.
     *
     * @param constructionSiteWork the constructionSiteWork to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new constructionSiteWork, or with status {@code 400 (Bad Request)} if the constructionSiteWork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/construction-site-works")
    public ResponseEntity<ConstructionSiteWork> createConstructionSiteWork(@Valid @RequestBody ConstructionSiteWork constructionSiteWork) throws URISyntaxException {
        log.debug("REST request to save ConstructionSiteWork : {}", constructionSiteWork);
        if (constructionSiteWork.getId() != null) {
            throw new BadRequestAlertException("A new constructionSiteWork cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(constructionSiteWork.getConstructionSite())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        long constructionSiteId = constructionSiteWork.getConstructionSite().getId();
        constructionSiteRepository.findById(constructionSiteId).ifPresent(constructionSiteWork::constructionSite);
        ConstructionSiteWork result = constructionSiteWorkRepository.save(constructionSiteWork);
        return ResponseEntity.created(new URI("/api/construction-site-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /construction-site-works} : Updates an existing constructionSiteWork.
     *
     * @param constructionSiteWork the constructionSiteWork to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated constructionSiteWork,
     * or with status {@code 400 (Bad Request)} if the constructionSiteWork is not valid,
     * or with status {@code 500 (Internal Server Error)} if the constructionSiteWork couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/construction-site-works")
    public ResponseEntity<ConstructionSiteWork> updateConstructionSiteWork(@Valid @RequestBody ConstructionSiteWork constructionSiteWork) throws URISyntaxException {
        log.debug("REST request to update ConstructionSiteWork : {}", constructionSiteWork);
        if (constructionSiteWork.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConstructionSiteWork result = constructionSiteWorkRepository.save(constructionSiteWork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, constructionSiteWork.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /construction-site-works} : get all the constructionSiteWorks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of constructionSiteWorks in body.
     */
    @GetMapping("/construction-site-works")
    @Transactional(readOnly = true)
    public List<ConstructionSiteWork> getAllConstructionSiteWorks() {
        log.debug("REST request to get all ConstructionSiteWorks");
        return constructionSiteWorkRepository.findAll();
    }

    /**
     * {@code GET  /construction-site-works/:id} : get the "id" constructionSiteWork.
     *
     * @param id the id of the constructionSiteWork to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the constructionSiteWork, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/construction-site-works/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ConstructionSiteWork> getConstructionSiteWork(@PathVariable Long id) {
        log.debug("REST request to get ConstructionSiteWork : {}", id);
        Optional<ConstructionSiteWork> constructionSiteWork = constructionSiteWorkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(constructionSiteWork);
    }

    /**
     * {@code DELETE  /construction-site-works/:id} : delete the "id" constructionSiteWork.
     *
     * @param id the id of the constructionSiteWork to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/construction-site-works/{id}")
    public ResponseEntity<Void> deleteConstructionSiteWork(@PathVariable Long id) {
        log.debug("REST request to delete ConstructionSiteWork : {}", id);
        constructionSiteWorkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
