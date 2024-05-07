package com.springcompletablefuture.controller;





import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springcompletablefuture.services.UserServices;

@RestController
public class UserController {
	
	@Autowired
	UserServices services;
	
	@PostMapping(value = "/saveusers",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = "application/json")
	public ResponseEntity saveUSers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
		for(MultipartFile file : files) {
			services.saveUsers(file);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value = "/getusers",produces ="application/json")
	public CompletableFuture<ResponseEntity> getUsers(){
		return services.getUsers().thenApply(ResponseEntity::ok);
	}
	
	@GetMapping("getcount")
	public String getCount() {
		return "total number of records" +services.getCount();
	}
	
}
