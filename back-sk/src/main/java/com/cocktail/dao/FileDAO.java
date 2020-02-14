package com.cocktail.dao;

import com.cocktail.model.UploadFile;

import org.springframework.data.repository.CrudRepository;
 
public interface FileDAO extends CrudRepository<UploadFile, Integer>{
}
