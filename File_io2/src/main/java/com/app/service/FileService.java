package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.Exception.FileException;
import com.app.enitity.FileEntity;
import com.app.repository.FileRepository;

@Service
@Transactional
public class FileService {
	
	// final modification  
//	public final String UPLOAD_DIR = new ClassPathResource("static/data/").getFile().getAbsolutePath(); 
           	//In my local machine UPLOAD_DIR = C:\Rushabh 19\github upload\File-IO\File_io2\target\classes\static\data
//	public final String UPLOAD_DIR = "C:\\Rushabh 19\\github upload\\File-IO\\File_io2\\src\\main\\resources\\static\\data";
	public final String UPLOAD_DIR = new ClassPathResource("static").getFile().getAbsolutePath();  //for dynamic file path target/static/
	// final modification   
	
	public FileService() throws IOException {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private FileRepository repo;
	

    // final modification---------------------------
    public String uploadFileToFileSystem(MultipartFile file) throws IOException{
    	
    String filePath = UPLOAD_DIR +File.separator;
    	filePath = filePath+LocalDate.now().getYear()+File.separator+LocalDate.now().getMonthValue()+File.separator+LocalDate.now().getDayOfMonth();
    	File newfolder = new File(filePath);
    	if(!newfolder.exists()) {
    	newfolder.mkdirs();
    	}	
     filePath = filePath + File.separator + file.getOriginalFilename();
    	
    	
    	 FileEntity fileobj = repo.save(FileEntity.builder()
    			.file_name(LocalDate.now()+"$$"+file.getOriginalFilename())   //need to be change 
    			.file_type(file.getContentType())   // ask which type
    			.created_at(LocalDate.now())
    			.modified_at(LocalDate.now())
    			.fileUuid(UUID.randomUUID().toString())
    			.filepath(filePath)
    			.status("file uploaded  at: "+LocalDate.now())
    			.data(file.getBytes())
    			.build()
    			);
    	 file.transferTo(new File(filePath));  //files were 
    	 
    	 if(fileobj != null) {
    		 return "file uploaded successfuly "+filePath+" \n, uuid: "+fileobj.getFileUuid();
    	 }
    	 return "something went wrong ";
    }
    
    
    // final modification---------------------------
    public byte[] downloadFileFromFileSystem(String uuid) throws IOException {
    	Optional<FileEntity> optionalFile = repo.findByFileUuid(uuid);
    	
    	String filePath = optionalFile.get().getFilepath();
    	optionalFile.get().setStatus("File is downloade on "+LocalDate.now());
    	optionalFile.get().setModified_at(LocalDate.now());
    	byte [] filedata = Files.readAllBytes(new File(filePath).toPath());
    	return filedata;
    }
    
}
