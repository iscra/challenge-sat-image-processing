package com.example.satimages.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Utility class which handles image processing.
 * It can combine 3 source images used as RGB channels into one image.
 * It uses simple AWT based processing.
 * Supports also saving image in JPG format.
 */

@Component
public class ImageProcessing {

	Logger logger = LoggerFactory.getLogger(ImageProcessing.class);

	public BufferedImage combineRGBChannels(BufferedImage imgRed, BufferedImage imgGreen, BufferedImage imgBlue) {

		logger.info("combineRGBChannels start");

		BufferedImage nonNullImage = null;
		if (imgRed != null) {
			nonNullImage = imgRed;
		} else if(imgGreen != null) {
			nonNullImage = imgGreen;
		} else if(imgBlue != null) {
			nonNullImage = imgBlue;
		} else {
			logger.error("combineRGBChannels got all 3 images as null");
			return null;
		}

		ImageFilter filter =  null;

		filter = new CombineRGBFilter( imgRed, imgGreen, imgBlue);
		int width = nonNullImage.getWidth();
		int height = nonNullImage.getHeight();

		Image sourceImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

		ImageProducer producer = new FilteredImageSource(
				sourceImage.getSource(),
				filter);
		Image image = Toolkit.getDefaultToolkit().createImage(producer);

		BufferedImage renderedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics bImageGraphics = renderedImage.getGraphics();
		bImageGraphics.drawImage(image, 0,0, null);

		logger.info("combineRGBChannels finished");

		return renderedImage;

	}

	public void saveImageAsJPG(BufferedImage image, OutputStream out) {
		try {
			ImageIO.write( image, "jpg", out);
			logger.info("saved as JPG image");		
		} catch (IOException e) {
			logger.error( "Converting to JPG failed", e);
			throw new ImageNotFoundException("Converting to JPG failed");

		}
	}


	/*
	 * CombineRGBFilter is used to actually combine RGB channels into target image.
	 * Performs simple pixel by pixel merge.
	 */
	class CombineRGBFilter extends RGBImageFilter {

		BufferedImage imgRed, imgGreen, imgBlue;

		public CombineRGBFilter( BufferedImage imageRed, BufferedImage imageGreen, BufferedImage imageBlue ) {
			imgRed = imageRed;
			imgGreen = imageGreen;
			imgBlue = imageBlue;
			canFilterIndexColorModel = false;
		}

		public int filterRGB(int x, int y, int rgb) {

			int red = imgRed != null ? imgRed.getRGB(x,y) & 0xff : 0;
			int green = imgGreen != null ? imgGreen.getRGB(x,y) & 0xff : 0;
			int blue = imgBlue != null ? imgBlue.getRGB(x,y) & 0xff : 0;

			int result = ( 0xff000000
					| (red << 16)
					| (green << 8)
					| (blue) );
			return result;

		}
	}

}

