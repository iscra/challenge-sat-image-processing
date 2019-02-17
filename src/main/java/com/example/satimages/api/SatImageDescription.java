package com.example.satimages.api;

import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * SatImageDescription defines the API structure used as input for /generate-images.
 * Java/Hibernate validation is used for basic data consistency checks.
 */

public class SatImageDescription {

	@NotNull
	@DecimalMin(value="1",inclusive=true)
	@DecimalMax(value="60",inclusive=true)
	private int utmZone;

	@NotNull
	@Size(min=1, max=1, message="latitudeBand should have 1 character")
	private String latitudeBand;

	@Size(min=1, max=2, message="gridSquare should have 2 characters")
	private String gridSquare;
	
	private LocalDate date;

	public enum ChannelMapping {
		VISIBLE,
		VEGETATION, 
		WATER_VAPOR; 
	}
	private ChannelMapping channelMap;

	public int getUtmZone() {
		return utmZone;
	}
	public void setUtmZone(int utmZone) {
		this.utmZone = utmZone;
	}
	public String getLatitudeBand() {
		return latitudeBand;
	}
	public void setLatitudeBand(String latitudeBand) {
		this.latitudeBand = latitudeBand;
	}
	public String getGridSquare() {
		return gridSquare;
	}
	public void setGridSquare(String gridSquare) {
		this.gridSquare = gridSquare;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public ChannelMapping getChannelMap() {
		return channelMap;
	}
	public void setChannelMap(ChannelMapping channelMap) {
		this.channelMap = channelMap;
	}

	@Override
	public String toString() {
		return "SatImageDescription [utmZone=" + utmZone + ", latitudeBand=" + latitudeBand + ", gridSquare="
				+ gridSquare + ", date=" + date + ", channelMap=" + channelMap + "]";
	}

}

