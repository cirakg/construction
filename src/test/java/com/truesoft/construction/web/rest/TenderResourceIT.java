package com.truesoft.construction.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.truesoft.construction.ConstructionApp;
import com.truesoft.construction.domain.Tender;
import com.truesoft.construction.domain.enumeration.TenderStatus;
import com.truesoft.construction.repository.TenderRepository;

/**
 * Integration tests for the {@link TenderResource} REST controller.
 */
@SpringBootTest(classes = ConstructionApp.class)

@AutoConfigureMockMvc
public class TenderResourceIT {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	private static final Instant DEFAULT_DATE_STARTED = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_DATE_STARTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	private static final TenderStatus DEFAULT_STATUS = TenderStatus.NEW;
	private static final TenderStatus UPDATED_STATUS = TenderStatus.ACTIVE;

	@Autowired
	private TenderRepository tenderRepository;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restTenderMockMvc;

	private Tender tender;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Tender createEntity(EntityManager em) {
		Tender tender = new Tender().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION)
				.dateCreated(DEFAULT_DATE_CREATED).dateStarted(DEFAULT_DATE_STARTED).status(DEFAULT_STATUS);
		return tender;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Tender createUpdatedEntity(EntityManager em) {
		Tender tender = new Tender().name(UPDATED_NAME).description(UPDATED_DESCRIPTION)
				.dateCreated(UPDATED_DATE_CREATED).dateStarted(UPDATED_DATE_STARTED).status(UPDATED_STATUS);
		return tender;
	}

	@BeforeEach
	public void initTest() {
		tender = createEntity(em);
	}

	@Test
	@Transactional
	public void createTender() throws Exception {
		int databaseSizeBeforeCreate = tenderRepository.findAll().size();

		// Create the Tender
		restTenderMockMvc.perform(post("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isCreated());

		// Validate the Tender in the database
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeCreate + 1);
		Tender testTender = tenderList.get(tenderList.size() - 1);
		assertThat(testTender.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testTender.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testTender.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
		assertThat(testTender.getDateStarted()).isEqualTo(DEFAULT_DATE_STARTED);
		assertThat(testTender.getStatus()).isEqualTo(DEFAULT_STATUS);
	}

	@Test
	@Transactional
	public void createTenderWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = tenderRepository.findAll().size();

		// Create the Tender with an existing ID
		tender.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restTenderMockMvc.perform(post("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isBadRequest());

		// Validate the Tender in the database
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = tenderRepository.findAll().size();
		// set the field null
		tender.setName(null);

		// Create the Tender, which fails.

		restTenderMockMvc.perform(post("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isBadRequest());

		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkDateCreatedIsRequired() throws Exception {
		int databaseSizeBeforeTest = tenderRepository.findAll().size();
		// set the field null
		tender.setDateCreated(null);

		// Create the Tender, which fails.

		restTenderMockMvc.perform(post("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isBadRequest());

		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkStatusIsRequired() throws Exception {
		int databaseSizeBeforeTest = tenderRepository.findAll().size();
		// set the field null
		tender.setStatus(null);

		// Create the Tender, which fails.

		restTenderMockMvc.perform(post("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isBadRequest());

		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllTenders() throws Exception {
		// Initialize the database
		tenderRepository.saveAndFlush(tender);

		// Get all the tenderList
		restTenderMockMvc.perform(get("/api/tenders?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(tender.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
				.andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
				.andExpect(jsonPath("$.[*].dateStarted").value(hasItem(DEFAULT_DATE_STARTED.toString())))
				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
	}

	@Test
	@Transactional
	public void getTender() throws Exception {
		// Initialize the database
		tenderRepository.saveAndFlush(tender);

		// Get the tender
		restTenderMockMvc.perform(get("/api/tenders/{id}", tender.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(tender.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
				.andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
				.andExpect(jsonPath("$.dateStarted").value(DEFAULT_DATE_STARTED.toString()))
				.andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingTender() throws Exception {
		// Get the tender
		restTenderMockMvc.perform(get("/api/tenders/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateTender() throws Exception {
		// Initialize the database
		tenderRepository.saveAndFlush(tender);

		int databaseSizeBeforeUpdate = tenderRepository.findAll().size();

		// Update the tender
		Tender updatedTender = tenderRepository.findById(tender.getId()).get();
		// Disconnect from session so that the updates on updatedTender are not directly
		// saved in db
		em.detach(updatedTender);
		updatedTender.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).dateCreated(UPDATED_DATE_CREATED)
				.dateStarted(UPDATED_DATE_STARTED).status(UPDATED_STATUS);

		restTenderMockMvc.perform(put("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updatedTender))).andExpect(status().isOk());

		// Validate the Tender in the database
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeUpdate);
		Tender testTender = tenderList.get(tenderList.size() - 1);
		assertThat(testTender.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testTender.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testTender.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
		assertThat(testTender.getDateStarted()).isEqualTo(UPDATED_DATE_STARTED);
		assertThat(testTender.getStatus()).isEqualTo(UPDATED_STATUS);
	}

	@Test
	@Transactional
	public void updateNonExistingTender() throws Exception {
		int databaseSizeBeforeUpdate = tenderRepository.findAll().size();

		// Create the Tender

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restTenderMockMvc.perform(put("/api/tenders").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isBadRequest());

		// Validate the Tender in the database
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteTender() throws Exception {
		// Initialize the database
		tenderRepository.saveAndFlush(tender);

		int databaseSizeBeforeDelete = tenderRepository.findAll().size();

		// Delete the tender
		restTenderMockMvc.perform(delete("/api/tenders/{id}", tender.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
