package com.spring.boot.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.demo.dao.ImageRepository;
import com.spring.boot.demo.entity.ImageInsert;

@Service
public class ImageServiceImpl implements ImageService {

	private ImageRepository imageRepository;
	
	@Autowired
	public ImageServiceImpl(ImageRepository theimageRepository) {
		imageRepository = theimageRepository;
	}
	
	@Override
	public List<ImageInsert> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public ImageInsert findById(int theId) {
		Optional<ImageInsert> result = imageRepository.findById(theId);
		
		ImageInsert theImages = null;
		
		if (result.isPresent()) {
			theImages = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find image id - " + theId);
		}
		
		return theImages;
	}

	@Override
	public void save(ImageInsert theImage) {
		imageRepository.save(theImage);
	}

	@Override
	public void deleteById(int theId) {
		imageRepository.deleteById(theId);
	}

	@Override
	public ImageInsert findByimagePath(String imagePath) {
	Optional<ImageInsert> result = imageRepository.findByimagePath(imagePath);
			
			ImageInsert theImages = null;
			
			if (result.isPresent()) {
				theImages = result.get();
			}
			else {
				
				throw new RuntimeException("Did not find image  - " + theImages);
			}
			
			return theImages;
	}

	

	

}






