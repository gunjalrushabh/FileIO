package com.app.controller;

import java.io.IOException;
import java.security.Provider.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.Exception.FileException;

import com.app.service.FileService;

@RestController
public class FileUploadController {


	@Autowired
	private FileService service;

	
	//final modification ------------
	@PostMapping("/folder")   //http://localhost:8083/folder
	public ResponseEntity<?> uploadFiletoFileSystem(@RequestParam("uploadfile")MultipartFile file){
		try {
			String uploadFile = service.uploadFileToFileSystem(file);
			
			return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FileException("File uploaded failed");
		}
		
	}
	
	@GetMapping("/filesystem/{uuid}")  // http://localhost:8083/filesystem/
//	public ResponseEntity<?> downloadFileFromFilSystem(@RequestParam(value = "file") String uuid) {
		public ResponseEntity<?> downloadFileFromFilSystem(@PathVariable String uuid) {
		byte [] filedate = null;
		try {
		    filedate = service.downloadFileFromFileSystem(uuid);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK)
			//	.contentType(MediaType.APPLICATION_PDF)
				.contentType(MediaType.IMAGE_PNG)
				.contentType(MediaType.IMAGE_JPEG)
				.contentType(MediaType.TEXT_PLAIN)   
				
				.body(filedate);
		
	}
}
