package models.dao;

import models.DepartmentNews;
import models.Department;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);
    void addDepartmentToDepartmentNews(Department department, DepartmentNews departmentNews);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<DepartmentNews> getAllDepartmentNewsByDepartment(int departmentId);

    //update
    void update(int id, String name, String company, String division, String phone, String website, String email);

    //delete
    void deleteById(int id);
    void clearAll();
}