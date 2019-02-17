package com.example.satimages.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.satimages.SatImagesApplication;
import com.fasterxml.jackson.databind.ObjectMapper;



/*
 * Integration test for the API
 */

@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SatImagesApplication.class)
@AutoConfigureMockMvc
public class ApiControllerIT {

	Logger logger = LoggerFactory.getLogger(ApiControllerIT.class);

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;


	@Test
	public void generateImagesReturnsImageForAvailableData()
			throws Exception {

		SatImageDescription desc = new SatImageDescription();
		desc.setUtmZone(33);
		desc.setLatitudeBand("U");
		desc.setGridSquare("UP");
		desc.setDate(LocalDate.of(2018, 8, 4));
		desc.setChannelMap(SatImageDescription.ChannelMapping.VISIBLE);

		logger.info("params " + objectMapper.writeValueAsString(desc));

		mvc.perform( post("/generate-images")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(desc)))
		.andExpect(status().isOk());

	}



}
