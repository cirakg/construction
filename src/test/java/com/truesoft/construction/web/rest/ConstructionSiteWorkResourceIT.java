package com.truesoft.construction.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.truesoft.construction.ConstructionApp;
import com.truesoft.construction.domain.ConstructionSite;
import com.truesoft.construction.domain.ConstructionSiteWork;
import com.truesoft.construction.domain.Work;
import com.truesoft.construction.domain.enumeration.ConstructionSiteWorkStatus;
import com.truesoft.construction.repository.ConstructionSiteRepository;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.WorkRepository;
import com.truesoft.construction.web.rest.dto.ConstructionSiteCreateDTO;
import com.truesoft.construction.web.rest.dto.ConstructionSiteWorkCreateDTO;

/**
 * Integration tests for the {@link ConstructionSiteWorkResource} REST
 * controller.
 */
@SpringBootTest(classes = ConstructionApp.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ConstructionSiteWorkResourceIT {

	private static final ConstructionSiteWorkStatus DEFAULT_STATUS = ConstructionSiteWorkStatus.NEW;

	@Autowired
	private ConstructionSiteWorkRepository constructionSiteWorkRepository;
	@Autowired
	private ConstructionSiteRepository constructionSiteRepository;
	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private MockMvc restConstructionSiteWorkMockMvc;
	
	private ConstructionSite cs;
	private Work work;
	private ConstructionSiteWorkCreateDTO constructionSiteWorkCreateDTO = new ConstructionSiteWorkCreateDTO();

	@BeforeEach
	public void initTest() throws InterruptedException {

		work = new Work("test work");
		work = workRepository.save(work);
		
		cs = new ConstructionSite("cs name", "cs desc");
		cs = constructionSiteRepository.save(cs);

		constructionSiteWorkCreateDTO = new ConstructionSiteWorkCreateDTO();
		List<Long> workIds = new ArrayList<Long>();
		workIds.add(work.getId());
		constructionSiteWorkCreateDTO.setWorkIds(workIds);
		constructionSiteWorkCreateDTO.setIssuerId(1l);
	}
	
	@Test
	@Transactional
	public void createConstructionSite() throws Exception {
		int databaseSizeBeforeCreate = constructionSiteRepository.findAll().size();

		ConstructionSiteCreateDTO dto = new ConstructionSiteCreateDTO();
		dto.setDescription("testD");
		dto.setIssuerId(1l);
		dto.setName("name");
		// Create the ConstructionSiteWork
		restConstructionSiteWorkMockMvc.perform(post("/api/construction-site").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated());

		// Validate the ConstructionSiteWork in the database
		List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
		assertThat(constructionSiteList).hasSize(databaseSizeBeforeCreate + 1);
		ConstructionSite testConstructionSite = constructionSiteList.get(constructionSiteList.size() - 1);
		assertThat(testConstructionSite.getDescription()).isEqualTo("testD");
		assertThat(testConstructionSite.getName()).isEqualTo("name");
	}
	
	@Test
	@Transactional
	public void addConstructionSiteWork() throws Exception {
		int databaseSizeBeforeCreate = constructionSiteWorkRepository.findAll().size();
		
		// Create the ConstructionSiteWork
		restConstructionSiteWorkMockMvc
				.perform(post("/api/construction-site/" + cs.getId() + "/work").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(constructionSiteWorkCreateDTO)))
				.andExpect(status().isOk());

		// Validate the ConstructionSiteWork in the database
		List<ConstructionSiteWork> constructionSiteWorkList = constructionSiteWorkRepository.findAll();
		assertThat(constructionSiteWorkList).hasSize(databaseSizeBeforeCreate + 1);
		ConstructionSiteWork testConstructionSiteWork = constructionSiteWorkList
				.get(constructionSiteWorkList.size() - 1);
		assertThat(testConstructionSiteWork.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testConstructionSiteWork.getWorkId()).isEqualTo(work.getId());
		assertThat(testConstructionSiteWork.getStatus()).isEqualTo(DEFAULT_STATUS);

	}



}
