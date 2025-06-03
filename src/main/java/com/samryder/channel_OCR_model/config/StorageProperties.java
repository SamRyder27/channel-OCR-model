package com.samryder.channel_OCR_model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties ("storage")
public class StorageProperties{
	
	private String location = "/Users/samryder/Projects/channel-OCR-model/doc-storage"; // upload directory

	
	public String getLocation() {
		return location;
	}

	
	public void setLocation(String location) {
		this.location = location;
	}
	
}