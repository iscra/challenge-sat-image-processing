package com.example.satimages.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDate;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.satimages.model.SatImageFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

	Logger logger = LoggerFactory.getLogger(ApiControllerTest.class);

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	SatImageFacade satImageGenerator;

	@Test
	public void generateImagesReturnsImageForAvailableData() throws Exception {

		SatImageDescription desc = new SatImageDescription();
		desc.setUtmZone(33);
		desc.setLatitudeBand("U");
		desc.setGridSquare("UP");
		desc.setDate(LocalDate.of(2018, 8, 4));
		desc.setChannelMap(SatImageDescription.ChannelMapping.VISIBLE);

		given(satImageGenerator.getImageStreamByDescription(any()))
		.willReturn( IOUtils.toInputStream("JPG content", Charset.defaultCharset()) );

		mvc.perform( post("/generate-images")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(desc)))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG));
	}

	@Test
	public void generateImagesReturnsNotFoundForMissingData()
			throws Exception {

		SatImageDescription desc = new SatImageDescription();
		desc.setUtmZone(33);
		desc.setLatitudeBand("U");
		desc.setGridSquare("UP");
		desc.setDate(LocalDate.of(2018, 8, 4));
		desc.setChannelMap(SatImageDescription.ChannelMapping.VISIBLE);

		given(satImageGenerator.getImageStreamByDescription(any()))
		.willReturn( null );

		mvc.perform( post("/generate-images")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(desc)))
		.andExpect(status().isNotFound());

	}

}
