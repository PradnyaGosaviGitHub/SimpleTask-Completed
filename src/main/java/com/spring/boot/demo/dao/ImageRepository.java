package com.spring.boot.demo.dao;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.demo.entity.ImageInsert;

public interface ImageRepository extends JpaRepository<ImageInsert, Integer> {

	Optional<ImageInsert> findByimagePath(String imagePath);

	
	
	
}
