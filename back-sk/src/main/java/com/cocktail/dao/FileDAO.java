package com.cocktail.dao;

import java.util.List;

import com.cocktail.model.UploadFile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
public interface FileDAO extends CrudRepository<UploadFile, Integer>{
    @Query(value= "select * from files where boardno = ?1", nativeQuery = true)
    List<UploadFile> list(int rid);
}
