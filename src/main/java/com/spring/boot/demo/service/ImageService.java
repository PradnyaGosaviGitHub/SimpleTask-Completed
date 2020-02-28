package com.spring.boot.demo.service;

import java.util.List;

import com.spring.boot.demo.entity.ImageInsert;

public interface ImageService {

	public List<ImageInsert> findAll();
	
	public ImageInsert findById(int theId);
	
	public void save(ImageInsert theImageInsert);
	
	public void deleteById(int theId);

	public ImageInsert findByimagePath(String imagePath);

	
}
