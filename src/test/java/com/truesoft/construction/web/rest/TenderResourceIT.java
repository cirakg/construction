package com.truesoft.construction.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.truesoft.construction.domain.ConstructionWorkCompositeId;
import com.truesoft.construction.domain.Offer;
import com.truesoft.construction.domain.Tender;
import com.truesoft.construction.domain.Work;
import com.truesoft.construction.domain.enumeration.TenderOfferStatus;
import com.truesoft.construction.domain.enumeration.TenderStatus;
import com.truesoft.construction.repository.ConstructionSiteRepository;
import com.truesoft.construction.repository.ConstructionSiteWorkRepository;
import com.truesoft.construction.repository.OfferRepository;
import com.truesoft.construction.repository.TenderRepository;
import com.truesoft.construction.repository.WorkRepository;
import com.truesoft.construction.service.AuthServiceStub;
import com.truesoft.construction.web.rest.dto.OfferDTO;
import com.truesoft.construction.web.rest.dto.TenderCreateDTO;

import io.undertow.util.BadRequestException;

/**
 * Integration tests for the {@link TenderResource} REST controller.
 */
@SpringBootTest(classes = ConstructionApp.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TenderResourceIT {

	@Autowired
	private TenderRepository tenderRepository;

	@Autowired
	private AuthServiceStub authServiceStub;

	@Autowired
	private MockMvc restTenderMockMvc;
	@Autowired
	private ConstructionSiteRepository constructionSiteRepository;
	@Autowired
	private ConstructionSiteWorkRepository constructionSiteWorkRepository;
	@Autowired
	private WorkRepository workRepository;
	@Autowired
	private OfferRepository offerRepository;

	private TenderCreateDTO tender;
	private ConstructionSite cs;
	private Work work;

	@BeforeEach
	public void initTest() throws BadRequestException {

		work = new Work("test work");
		work = workRepository.save(work);

		cs = new ConstructionSite("cs name", "cs desc");
		cs = constructionSiteRepository.save(cs);

		constructionSiteWorkRepository.save(new ConstructionSiteWork(cs.getId(), work.getId()));

		tender = new TenderCreateDTO();

		List<ConstructionWorkCompositeId> constructionSiteWorkIds = new ArrayList<ConstructionWorkCompositeId>();
		constructionSiteWorkIds.add(new ConstructionWorkCompositeId(cs.getId(), work.getId()));

		tender.setConstructionSiteWorkIds(constructionSiteWorkIds);
		tender.setDescription("testD");
		tender.setIssuerId(1l);
		tender.setName("testN");
	}

	@Test
	@Transactional
	public void createTender() throws Exception {
		int databaseSizeBeforeCreate = tenderRepository.findAll().size();

		// Create the Tender
		restTenderMockMvc.perform(post("/api/tender").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(tender))).andExpect(status().isCreated());

		// Validate the Tender in the database
		List<Tender> tenderList = tenderRepository.findAll();
		assertThat(tenderList).hasSize(databaseSizeBeforeCreate + 1);
		Tender testTender = tenderList.get(tenderList.size() - 1);
		assertThat(testTender.getName()).isEqualTo("testN");
		assertThat(testTender.getDescription()).isEqualTo("testD");
		assertThat(testTender.getDateCreated()).isNotNull();
		assertThat(testTender.getDateStarted()).isNotNull();
		assertThat(testTender.getStatus()).isEqualTo(TenderStatus.ACTIVE);
	}

	@Test
	@Transactional
	public void createOffer() throws Exception {
		
		// create tender first
		Tender t = new Tender("t", "td", authServiceStub.getIssuer(1l));
		t = tenderRepository.save(t);
		
		OfferDTO dto = new OfferDTO();
		dto.setBidderId(1l);
		dto.setDescription("offer desc");
		dto.setPrice(3232d);
		
		int databaseSizeBeforeCreate = offerRepository.findAll().size();
		restTenderMockMvc.perform(post("/api/tender/" + t.getId() + "/offer").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isCreated());
		// Validate the Tender in the database
		List<Offer> offerList = offerRepository.findAll();
		assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
		Offer testOffer = offerList.get(offerList.size() - 1);
		assertThat(testOffer.getPrice()).isEqualTo(3232d);
		assertThat(testOffer.getDescription()).isEqualTo("offer desc");
		assertThat(testOffer.getStatus()).isEqualTo(TenderOfferStatus.PENDING);
	}
	
	@Test
	@Transactional
	public void acceptOffer() throws Exception {
		
		// create tender first
		Tender t = new Tender("t", "td", authServiceStub.getIssuer(1l));
		t = tenderRepository.save(t);
		
		Offer o = new Offer(22d, "test", t, authServiceStub.getBidder(1l));
		o = offerRepository.save(o);
		
		OfferDTO dto = new OfferDTO();
		dto.setBidderId(1l);
		dto.setDescription("offer desc");
		dto.setPrice(3232d);
		
		int databaseSizeBeforeCreate = offerRepository.findAll().size();
		restTenderMockMvc.perform(put("/api/tender/" + t.getId() + "/offer/" + o.getId() + "?issuerId=1").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(dto))).andExpect(status().isAccepted());
		// Validate the offer in the database
		List<Offer> offerList = offerRepository.findAll();
		assertThat(offerList).hasSize(databaseSizeBeforeCreate);
		Offer testOffer = offerList.get(offerList.size() - 1);
		assertThat(testOffer.getPrice()).isEqualTo(22d);
		assertThat(testOffer.getDescription()).isEqualTo("test");
		assertThat(testOffer.getStatus()).isEqualTo(TenderOfferStatus.ACCEPTED);
		
	}

}
