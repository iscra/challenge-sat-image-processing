package com.example.satimages.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.satimages.api.SatImageDescription;

@RunWith(SpringRunner.class)
public class SatImagesFileStoreTest {

	Logger logger = LoggerFactory.getLogger(SatImagesFileStoreTest.class);

	SatImageFileStore store = new SatImageFileStore();
	
	@Test
	public void createProperFileNamesTest() {

		int satChannel = 3;

		SatImageDescription desc = new SatImageDescription();
		desc.setUtmZone(33);
		desc.setLatitudeBand("U");
		desc.setGridSquare("UP");
		desc.setDate(LocalDate.of(2018, 8, 4));
		desc.setChannelMap(SatImageDescription.ChannelMapping.VISIBLE);

		String name = store.getFileName(desc,satChannel);
		assertEquals( "T33UUP_20180804T100031_B03.tif", name );

	}

}
