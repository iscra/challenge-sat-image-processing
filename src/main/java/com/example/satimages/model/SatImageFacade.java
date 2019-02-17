package com.example.satimages.model;

import java.io.InputStream;

import com.example.satimages.api.SatImageDescription;

/*
 * SatImageFacade defines the interface for actual service logic,
 * just one operation which returns satellite image based on the supplied description.
 */

public interface SatImageFacade {

	/*
	 * Get image as specified by the description.
	 * Returns image in JPG format as InputStream.
	 * When data is not available, throws exception.
	 */
	public InputStream getImageStreamByDescription(SatImageDescription desc);		

	
}
