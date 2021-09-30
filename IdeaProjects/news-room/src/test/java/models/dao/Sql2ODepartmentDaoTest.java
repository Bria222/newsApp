package models.dao;

import models.DepartmentNews;
import models.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2ODepartmentDaoTest {
    private Connection conn;
    private Sql2ODepartmentDao departmentDao;
    private Sql2ODepartmentNewsDao departmentNewsDao;
    private Sql2ONewsDetailsDao newsDetailsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2ODepartmentDao(sql2o);
        departmentNewsDao = new Sql2ODepartmentNewsDao(sql2o);
        newsDetailsDao = new Sql2ONewsDetailsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        Department testDepartment = setupDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());
    }

    @Test
    public void addedRestaurantsAreReturnedFromGetAll() throws Exception {
        Department testDepartment = setupDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void noRestaurantsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectRestaurant() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.update(testDepartment.getId(), "a", "b", "c", "d", "e", "f");
        Department foundDepartment = departmentDao.findById(testDepartment.getId());
        assertEquals("a", foundDepartment.getName());
        assertEquals("b", foundDepartment.getCompany());
        assertEquals("c", foundDepartment.getDivision());
        assertEquals("d", foundDepartment.getPhone());
        assertEquals("e", foundDepartment.getWebsite());
        assertEquals("f", foundDepartment.getEmail());
    }

    @Test
    public void deleteByIdDeletesCorrectRestaurant() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void getAllDepartmentNewsForADepartmentReturnsDepartmentNewsCorrectly() throws Exception {
        DepartmentNews testDepartmentNews = new DepartmentNews("Seafood");
        departmentNewsDao.add(testDepartmentNews);

        DepartmentNews otherDepartmentNews = new DepartmentNews("Bar Food");
        departmentNewsDao.add(otherDepartmentNews);

        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        departmentDao.addDepartmentToDepartmentNews(testDepartment, testDepartmentNews);
        departmentDao.addDepartmentToDepartmentNews(testDepartment, otherDepartmentNews);

        DepartmentNews[] departmentNews = {testDepartmentNews, otherDepartmentNews}; //oh hi what is this?

        assertEquals(Arrays.asList(departmentNews), departmentDao.getAllDepartmentNewsByDepartment(testDepartment.getId()));
    }

    @Test
    public void deleteingDepartmentAlsoUpdatesJoinTable() throws Exception {
        DepartmentNews testDepartmentNews = new DepartmentNews("Seafood");
        departmentNewsDao.add(testDepartmentNews);

        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);

        Department altDepartment = setupAltDepartment();
        departmentDao.add(altDepartment);

        departmentDao.addDepartmentToDepartmentNews(testDepartment, testDepartmentNews);
        departmentDao.addDepartmentToDepartmentNews(altDepartment, testDepartmentNews);

        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAllDepartmentNewsByDepartment(testDepartment.getId()).size());
    }


    //helpers

    public Department setupDepartment(){
        Department department = new Department("Fish Omena", "214 NE Ngara", "97232", "254-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        departmentDao.add(department);
        return department;
    }

    public Department setupAltDepartment (){
        Department department = new Department("Fish Omena", "214 NE Ngara", "97232", "254-402-9874");
        departmentDao.add(department);
        return department;
    }
}
