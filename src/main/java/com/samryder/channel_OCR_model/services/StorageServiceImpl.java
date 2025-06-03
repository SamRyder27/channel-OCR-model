package com.samryder.channel_OCR_model.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.samryder.channel_OCR_model.config.StorageException;
import com.samryder.channel_OCR_model.config.StorageProperties;

@Service
class StorageServiceImpl implements StorageService{
	
	private final Path rootLocation;
	
	@Autowired
	StorageProperties properties;
	public StorageServiceImpl(StorageProperties properties) {
		
		if (properties.getLocation().trim().length() == 0) {
			throw new StorageException ("File upload location cannot be empty");
		}
		this.rootLocation = Paths.get(properties.getLocation());
		
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException ("Could not initialize storagw", e);
		}
		
		
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException ("Failed to store... empty file.");
			}
			
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException ("Cannot store file outside currecnt directory");
			}
			
			try (InputStream inputStream = file.getInputStream()){
				Files.copy (inputStream, destinationFile,StandardCopyOption.REPLACE_EXISTING);
				
			}
			
		}
		catch (IOException e) {
			throw new StorageException ("Failed to store, error occured");
			
		}
		
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(
					path -> !path.equals(this.rootLocation))
					.map(this.rootLocation :: relativize);
					
		}
		catch (IOException e) {
			throw new StorageException ("Failed to read sotred files", e);
			
		}
	
	}

//	@Override
//	public Resource loadAsResources(String filename) {
//		try {
//			Path file = load(filename);
//			Resource resource = new UrlResource(files.toUri());
//			
//		}
//		
//	}

	@Override
	public void deleteAll() {
		
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		
	}
	
}