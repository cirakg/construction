package com.truesoft.construction.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truesoft.construction.repository.TenderRepository;

/**
 * REST controller for managing {@link com.truesoft.construction.domain.Tender}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TenderResource {

	private final Logger log = LoggerFactory.getLogger(TenderResource.class);

	private final TenderRepository tenderRepository;

	public TenderResource(TenderRepository tenderRepository) {
		this.tenderRepository = tenderRepository;
	}

}
