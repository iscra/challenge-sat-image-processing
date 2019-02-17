package com.example.satimages.model;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.satimages.api.SatImageDescription;

/*
 * SatImageGenerator implements SatImageFacade.
 * It combines the single channel satellite files into one RGB image
 * and returns it as InputStream for JPG image.
 * It uses only memory, the image is neither cached nor stored.
 */

@Component
public class SatImageGenerator implements SatImageFacade {

	Logger logger = LoggerFactory.getLogger(SatImageGenerator.class);

	@Autowired
	SatImageFileStore fileStore;

	@Autowired
	ImageProcessing imageProcessing;

	@Override
	public InputStream getImageStreamByDescription(SatImageDescription desc) {

		List<Integer> rgbChannels = getRGBChannelMapping( desc.getChannelMap() );
		
		List<BufferedImage> images = fileStore.loadChannelImages( desc, rgbChannels );
		BufferedImage combined = imageProcessing.combineRGBChannels( images.get(0), images.get(1), images.get(2));

		ByteArrayOutputStream inMemoryBuffer = new ByteArrayOutputStream();
		imageProcessing.saveImageAsJPG(combined, inMemoryBuffer);
		InputStream in = inMemoryBuffer.toInputStream();
		return in;
	}
	
	/*
	 * Map named channel mapping into actual channel numbers of satellite images.
	 */
	
	private	List<Integer> getRGBChannelMapping(SatImageDescription.ChannelMapping mapping) {
		List<Integer> rgbChannels = new ArrayList<>();

		switch(mapping) {
		case VISIBLE:
			rgbChannels.add(4);
			rgbChannels.add(3);
			rgbChannels.add(2);
			break;
		case VEGETATION:
			rgbChannels.add(5);
			rgbChannels.add(6);
			rgbChannels.add(7);
			break;
		case WATER_VAPOR:
			rgbChannels.add(null);
			rgbChannels.add(null);
			rgbChannels.add(9);
			break;
		}
		logger.info("getRGBChannelMapping {}",rgbChannels);
		return rgbChannels;
	}

}
