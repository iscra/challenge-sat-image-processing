package com.example.satimages.api;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.satimages.model.ImageNotFoundException;
import com.example.satimages.model.SatImageFacade;
import com.example.satimages.model.SatImageGenerator;

/*
 * API Rest based controller which handles the /generate-images endpoint.
 * It uses SatImageFacade to perform the actual image generation.
 * Returns directly the generated image in JPG format.
 * Returns 404 in case when required source images are missing.
 * Returns 400 if arguments are not correct for SatImageDescription.
 * Both 400 and 404 include JSON body with text error message.
 */

@RestController
public class ApiController {

	Logger logger = LoggerFactory.getLogger(SatImageGenerator.class);

	@Autowired
	SatImageFacade satImageFacade;

	@PostMapping(
			value = "/generate-images",
			produces = MediaType.IMAGE_JPEG_VALUE
			)
	public @ResponseBody byte[] generateImages( @Valid @RequestBody SatImageDescription satImageDescription)  {

		logger.info("generateImages " + satImageDescription);
		InputStream in = satImageFacade.getImageStreamByDescription(satImageDescription);
		if (in != null) {
			try {
				return IOUtils.toByteArray(in);
			} catch (IOException e) {
				logger.error( "Image generation failed", e);
				throw new ImageNotFoundException(e.getMessage());
			}
		} else {
			throw new ImageNotFoundException("image not found");
		}
	}
}


