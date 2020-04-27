package com.truesoft.construction.web.rest;

import com.truesoft.construction.ConstructionApp;
import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.ConstructionSiteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.truesoft.construction.domain.enumeration.ConstructionSiteWorkStatus;
/**
 * Integration tests for the {@link ConstructionSiteWorkResource} REST controller.
 */
@SpringBootTest(classes = ConstructionApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ConstructionSiteWorkResourceIT {

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ConstructionSiteWorkStatus DEFAULT_STATUS = ConstructionSiteWorkStatus.NEW;
    private static final ConstructionSiteWorkStatus UPDATED_STATUS = ConstructionSiteWorkStatus.ACTIVE;

    @Autowired
    private ConstructionSiteWorkRepository constructionSiteWorkRepository;
    @Autowired
    private ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConstructionSiteWorkMockMvc;

    private ConstructionSiteWork constructionSiteWork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConstructionSiteWork createEntity(EntityManager em) {
        ConstructionSiteWork constructionSiteWork = new ConstructionSiteWork()
            .dateCreated(DEFAULT_DATE_CREATED)
            .status(DEFAULT_STATUS);
        // Add required entity
        ConstructionSite constructionSite;
        if (TestUtil.findAll(em, ConstructionSite.class).isEmpty()) {
            constructionSite = ConstructionSiteResourceIT.createEntity(em);
            em.persist(constructionSite);
            em.flush();
        } else {
            constructionSite = TestUtil.findAll(em, ConstructionSite.class).get(0);
        }
        constructionSiteWork.setConstructionSite(constructionSite);
        return constructionSiteWork;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConstructionSiteWork createUpdatedEntity(EntityManager em) {
        ConstructionSiteWork constructionSiteWork = new ConstructionSiteWork()
            .dateCreated(UPDATED_DATE_CREATED)
            .status(UPDATED_STATUS);
        // Add required entity
        ConstructionSite constructionSite;
        if (TestUtil.findAll(em, ConstructionSite.class).isEmpty()) {
            constructionSite = ConstructionSiteResourceIT.createUpdatedEntity(em);
            em.persist(constructionSite);
            em.flush();
        } else {
            constructionSite = TestUtil.findAll(em, ConstructionSite.class).get(0);
        }
        constructionSiteWork.setConstructionSite(constructionSite);
        return constructionSiteWork;
    }

    @BeforeEach
    public void initTest() {
        constructionSiteWork = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstructionSiteWork() throws Exception {
        int databaseSizeBeforeCreate = constructionSiteWorkRepository.findAll().size();

        // Create the ConstructionSiteWork
        restConstructionSiteWorkMockMvc.perform(post("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteWork)))
            .andExpect(status().isCreated());

        // Validate the ConstructionSiteWork in the database
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeCreate + 1);
        ConstructionSiteWork testConstructionSiteWork = constructionSiteWorkList.get(constructionSiteWorkList.size() - 1);
        assertThat(testConstructionSiteWork.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testConstructionSiteWork.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the id for MapsId, the ids must be same
        assertThat(testConstructionSiteWork.getId()).isEqualTo(testConstructionSiteWork.getConstructionSite().getId());
    }

    @Test
    @Transactional
    public void createConstructionSiteWorkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constructionSiteWorkRepository.findAll().size();

        // Create the ConstructionSiteWork with an existing ID
        constructionSiteWork.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstructionSiteWorkMockMvc.perform(post("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteWork)))
            .andExpect(status().isBadRequest());

        // Validate the ConstructionSiteWork in the database
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateConstructionSiteWorkMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        constructionSiteWorkRepository.saveAndFlush(constructionSiteWork);
        int databaseSizeBeforeCreate = constructionSiteWorkRepository.findAll().size();

        // Add a new parent entity
        ConstructionSite constructionSite = ConstructionSiteResourceIT.createUpdatedEntity(em);
        em.persist(constructionSite);
        em.flush();

        // Load the constructionSiteWork
        ConstructionSiteWork updatedConstructionSiteWork = constructionSiteWorkRepository.findById(constructionSiteWork.getId()).get();
        // Disconnect from session so that the updates on updatedConstructionSiteWork are not directly saved in db
        em.detach(updatedConstructionSiteWork);

        // Update the ConstructionSite with new association value
        updatedConstructionSiteWork.setConstructionSite(constructionSite);

        // Update the entity
        restConstructionSiteWorkMockMvc.perform(put("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConstructionSiteWork)))
            .andExpect(status().isOk());

        // Validate the ConstructionSiteWork in the database
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeCreate);
        ConstructionSiteWork testConstructionSiteWork = constructionSiteWorkList.get(constructionSiteWorkList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testConstructionSiteWork.getId()).isEqualTo(testConstructionSiteWork.getConstructionSite().getId());
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = constructionSiteWorkRepository.findAll().size();
        // set the field null
        constructionSiteWork.setDateCreated(null);

        // Create the ConstructionSiteWork, which fails.

        restConstructionSiteWorkMockMvc.perform(post("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteWork)))
            .andExpect(status().isBadRequest());

        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = constructionSiteWorkRepository.findAll().size();
        // set the field null
        constructionSiteWork.setStatus(null);

        // Create the ConstructionSiteWork, which fails.

        restConstructionSiteWorkMockMvc.perform(post("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteWork)))
            .andExpect(status().isBadRequest());

        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConstructionSiteWorks() throws Exception {
        // Initialize the database
        constructionSiteWorkRepository.saveAndFlush(constructionSiteWork);

        // Get all the constructionSiteWorkList
        restConstructionSiteWorkMockMvc.perform(get("/api/construction-site-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constructionSiteWork.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getConstructionSiteWork() throws Exception {
        // Initialize the database
        constructionSiteWorkRepository.saveAndFlush(constructionSiteWork);

        // Get the constructionSiteWork
        restConstructionSiteWorkMockMvc.perform(get("/api/construction-site-works/{id}", constructionSiteWork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(constructionSiteWork.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConstructionSiteWork() throws Exception {
        // Get the constructionSiteWork
        restConstructionSiteWorkMockMvc.perform(get("/api/construction-site-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstructionSiteWork() throws Exception {
        // Initialize the database
        constructionSiteWorkRepository.saveAndFlush(constructionSiteWork);

        int databaseSizeBeforeUpdate = constructionSiteWorkRepository.findAll().size();

        // Update the constructionSiteWork
        ConstructionSiteWork updatedConstructionSiteWork = constructionSiteWorkRepository.findById(constructionSiteWork.getId()).get();
        // Disconnect from session so that the updates on updatedConstructionSiteWork are not directly saved in db
        em.detach(updatedConstructionSiteWork);
        updatedConstructionSiteWork
            .dateCreated(UPDATED_DATE_CREATED)
            .status(UPDATED_STATUS);

        restConstructionSiteWorkMockMvc.perform(put("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConstructionSiteWork)))
            .andExpect(status().isOk());

        // Validate the ConstructionSiteWork in the database
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeUpdate);
        ConstructionSiteWork testConstructionSiteWork = constructionSiteWorkList.get(constructionSiteWorkList.size() - 1);
        assertThat(testConstructionSiteWork.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testConstructionSiteWork.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingConstructionSiteWork() throws Exception {
        int databaseSizeBeforeUpdate = constructionSiteWorkRepository.findAll().size();

        // Create the ConstructionSiteWork

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConstructionSiteWorkMockMvc.perform(put("/api/construction-site-works")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteWork)))
            .andExpect(status().isBadRequest());

        // Validate the ConstructionSiteWork in the database
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConstructionSiteWork() throws Exception {
        // Initialize the database
        constructionSiteWorkRepository.saveAndFlush(constructionSiteWork);

        int databaseSizeBeforeDelete = constructionSiteWorkRepository.findAll().size();

        // Delete the constructionSiteWork
        restConstructionSiteWorkMockMvc.perform(delete("/api/construction-site-works/{id}", constructionSiteWork.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
        assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
