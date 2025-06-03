package com.samryder.channel_OCR_model.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.samryder.channel_OCR_model.services.StorageService;

class Controller{
	
	@Autowired
	private StorageService storageService;
	public Controller(StorageService storageService) {
		
		this.storageService = storageService;
		// TODO Auto-generated constructor stub
	}
	
	
}