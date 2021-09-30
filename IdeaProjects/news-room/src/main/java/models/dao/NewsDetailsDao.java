package models.dao;

import models.NewsDetails;

import java.util.Collection;
import java.util.List;

public interface NewsDetailsDao {

    //create
      void add(NewsDetails newsDetails);

    //read
    List<NewsDetails> getAll();

     List<NewsDetails> getAllNewsDetailsByDepartment(int departmentId);

    List<NewsDetails> getAllNewsDetailsByDepartmentSortedNewestToOldest(int departmentId);
    //update


    //delete
    void deleteById(int id);
    void clearAll();



}

