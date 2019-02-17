package com.example.satimages.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * ImageNotFoundException is an API error.
 * It returns 404 status and text message.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageNotFoundException(String message) {
		super(message);
	}

}
