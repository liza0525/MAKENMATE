package com.cocktail.dao;

import java.util.List;

import com.cocktail.model.UploadFile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
 
public interface FileDAO extends CrudRepository<UploadFile, Integer>{
    @Query("select * from files where boardno = :rid")
    List<UploadFile> list();
}
