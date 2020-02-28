package com.spring.boot.demo.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.boot.demo.entity.ImageInsert;
import com.spring.boot.demo.service.ImageService;;

@Controller
@RequestMapping("/images")
public class ImageShowRestController {

		private ImageService imageService;
		final Logger logger = LoggerFactory.getLogger(ImageShowRestController.class);
		
		public ImageShowRestController(ImageService theImageService) {
			imageService = theImageService;
		}
		
		@GetMapping("/list")
		public String listEmployees(Model theModel) {
			
			
			List<ImageInsert> theImages = imageService.findAll();
			
			// add to the spring model
			theModel.addAttribute("images", theImages);
			
			return "images/list-images";
		}
		
		@GetMapping("/showFormForAdd")
		public String showFormForAdd(Model theModel) {
			ImageInsert theImageInsert = new ImageInsert();
			theModel.addAttribute("image",theImageInsert);
			return "images/image-form";
		}
		
		@PostMapping("/save")
		public String saveImage(@ModelAttribute("image") ImageInsert theImage) {
			
			theImage.setId(0);

			String toEncode = theImage.getImagePath();

			// Encode into Base64 format
			String BasicBase64format = Base64.getEncoder().encodeToString(toEncode.getBytes());
			theImage.setImagePath(BasicBase64format);
			logger.info("Encoded Path "+BasicBase64format);
			imageService.save(theImage);
			
			return "redirect:/images/list";
			
		}
		
		@PostMapping("/showFormForUpdate")
		public String showFormForUpdate(@RequestParam("imageId") int theId,
										Model theModel) {
			
			ImageInsert theImageInsert = imageService.findById(theId);
			
			theModel.addAttribute("image", theImageInsert);
			
			return "images/image-form";			
		}
		
		@PostMapping("/delete")
		public String delete(@RequestParam("imageId") int theId) {
			
			imageService.deleteById(theId);
			return "redirect:/images/list";
			
		}
		
		@PostMapping("/download")
		public String download(@RequestParam("imageId") int imageId) {			
			
			ImageInsert theImage = imageService.findById(imageId);
			
			byte[] actualByte = Base64.getDecoder().decode(theImage.getImagePath());
			String con = new String(actualByte);
			System.out.println(con);
			
			
			
			File file = new File(con);
			 
	        try {            
	            // Reading a Image file from file system
	            FileInputStream imageInFile = new FileInputStream(file);
	            byte imageData[] = new byte[(int) file.length()];
	            imageInFile.read(imageData);
	 
	            // Converting Image byte array into Base64 String
	            String imageDataString = encodeImage(imageData);
	 
	            // Converting a Base64 String into Image byte array
	            byte[] imageByteArray = decodeImage(imageDataString);
	 
	            // Write a image byte array into file system
	            String path = "C:/Users/Kalyani/home/";
	            FileOutputStream imageOutFile = new FileOutputStream(path+imageId+".jpg");
	 
	            imageOutFile.write(imageByteArray);
	 
	            imageInFile.close();
	            imageOutFile.close();
	 
	            System.out.println("Image Successfully Manipulated!");
	        } catch (FileNotFoundException e) {
	            System.out.println("Image not found" + e);
	        } catch (IOException ioe) {
	            System.out.println("Exception while reading the Image " + ioe);
	        }

			return "images/show-image";
			
		}
		
		public static String encodeImage(byte[] imageByteArray) {
	        return	org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(imageByteArray);
	    }
		
		
		
		 public static byte[] decodeImage(String imageDataString) {
			 
		        return org.apache.commons.codec.binary.Base64.decodeBase64(imageDataString);
		    }
}
