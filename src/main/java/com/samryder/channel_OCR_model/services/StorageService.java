package com.samryder.channel_OCR_model.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	void init();
	void store (MultipartFile file);
	
	Stream<Path> loadAll ();
	//Resource loadAsResources (String filename);
	
	void deleteAll ();
	
	

}
