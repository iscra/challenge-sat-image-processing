package com.example.satimages.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.satimages.api.SatImageDescription;

/*
 * SatImageFileStore handles access to satellite images stored as local files,
 * named by the given convention:
 * T[UTM_ZONE][LATITUDE_BAND] [GRID_SQUARE]_[ACQUISITION_DATETIME]_[SENSOR_BAND].tif
 * 
 */

@Component
public class SatImageFileStore {

	Logger logger = LoggerFactory.getLogger(SatImageFileStore.class);

	/*
	 * configurable directory path to image directory
	 */
	@Value( "${images.dir}" )
	private String dirPath;

	/*
	 * load multiple channel images, according to channel list
	 * list can contain null entries, for which null image reference will be return too
	 * throws ImageNotFoundException when source images are missing
	 */
	public List<BufferedImage> loadChannelImages(SatImageDescription desc, List<Integer> rgbChannels) {
		List<BufferedImage> result = new ArrayList<>();
		rgbChannels.forEach( (channel) -> {
			logger.debug("loadChannelImages channel {}", channel);
			BufferedImage image = null;
			if (channel != null) {
				image = loadChannelImage(desc,channel.intValue());
			}
			logger.debug("loadChannelImages image {}", image);
			result.add( image );
		} );
		return result;
	}

	/*
	 * load single image
	 */
	protected BufferedImage loadChannelImage(SatImageDescription desc, int satChannel) {
		String filename = getFileName(desc, satChannel);
		BufferedImage image;
		try {
			String fullpath = Paths.get( dirPath, filename).toString();
			logger.info("loading file {}",fullpath);
			image = ImageIO.read(new File(fullpath));
			logger.info("loaded image {}",image);
			return image;
		} catch (IOException e) {
			logger.error( "Error loading file " + filename, e);
			throw new ImageNotFoundException("Source channel image file not found");
		}
	}

	/*
	 * get file name according to the convention
	 */
	protected String getFileName(SatImageDescription desc, int satChannel) {
		String dateAsString = desc.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String sensorBand = String.format("100031_B%02d", satChannel);
		String filename = String.format( 
				"T%02d%s%s_%sT%s.tif", 
				desc.getUtmZone(), desc.getLatitudeBand(), desc.getGridSquare(),
				dateAsString,
				sensorBand
				);
		logger.debug("getFileName {}", filename);
		return filename;

	}

}
