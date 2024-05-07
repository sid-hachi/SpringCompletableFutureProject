package com.springcompletablefuture.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springcompletablefuture.model.User;
import com.springcompletablefuture.repositories.UserRepo;

@Service
public class UserServices {
	@Autowired
	UserRepo repo;
	
	Logger logger = LoggerFactory.getLogger(UserServices.class);
	
	public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception{
		long start = System.currentTimeMillis();
		List<User> list = parseCSV(file);
		logger.info("Saving the entries from the CSV {}",list.size()," by "+Thread.currentThread());
		repo.saveAll(list);
		long end = System.currentTimeMillis();
		logger.info("Time taken by Thread to complete {}",end - start);
		return CompletableFuture.completedFuture(list);	
	}
	
	public CompletableFuture<List<User>> getUsers(){
		logger.info("Fetching all the user from the database");
		List<User> list=repo.findAll();
		return CompletableFuture.completedFuture(list);
	}
	
	
	public List<User> parseCSV(MultipartFile file) throws Exception{
		List<User> list = new ArrayList<>();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			while((line=reader.readLine())!=null) {
				String arr[]=line.split(",");
				User user = new User();
				user.setName(arr[0]);
				user.setEmail(arr[1]);
				user.setGender(arr[2]);
				list.add(user);
			}
		} catch (IOException e) {
			logger.error("Failed to parse the CSV file {}",e);
			throw new Exception("Failed to parse the CSV file {}",e);
		}
		
		return list;
	}
	
	public String getCount() {
		return repo.count() +"";
	}

}
