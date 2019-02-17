package com.example.satimages.api;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SatImagesDescriptionTest {

	Logger logger = LoggerFactory.getLogger(SatImagesDescriptionTest.class);

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void createValidSampleTest() {

		SatImageDescription desc = new SatImageDescription();
		desc.setUtmZone(33);
		desc.setLatitudeBand("U");
		desc.setGridSquare("UP");
		desc.setDate(LocalDate.of(2018, 8, 4));
		desc.setChannelMap(SatImageDescription.ChannelMapping.VISIBLE);

		Set<ConstraintViolation<SatImageDescription>> constraintViolations = validator.validate(desc);
		assertEquals(0, constraintViolations.size());

	}

}
