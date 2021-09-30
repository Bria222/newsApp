package models.dao;

import models.DepartmentNews;
import models.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2ODepartmentNewsDaoTest {
    private Sql2ODepartmentNewsDao departmentNewsDao;
    private Sql2ODepartmentDao departmentDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {

      String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2ODepartmentDao(sql2o);
        departmentNewsDao = new Sql2ODepartmentNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        DepartmentNews testDepartmentNews = setupNewDepartmentNews();
        int originalDepartmentNewsId = testDepartmentNews.getId();
        departmentNewsDao.add(testDepartmentNews);
        assertNotEquals(originalDepartmentNewsId, testDepartmentNews.getId());
    }

    @Test
    public void addedDepartmentNewsAreReturnedFromGetAll() throws Exception {
        DepartmentNews departmentNews = setupNewDepartmentNews();
        departmentNewsDao.add(departmentNews);
        assertEquals(1, departmentNewsDao.getAll().size());
    }

    @Test
    public void noDepartmentNewsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentNewsDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectDepartmentNews() throws Exception {
        DepartmentNews departmentNews = setupNewDepartmentNews();
        departmentNewsDao.add(departmentNews);
        departmentNewsDao.deleteById(departmentNews.getId());
        assertEquals(0, departmentNewsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        DepartmentNews testDepartmentNews = setupNewDepartmentNews();
        DepartmentNews otherDepartmentNews = setupNewDepartmentNews();
        departmentNewsDao.clearAll();
        assertEquals(0, departmentNewsDao.getAll().size());
    }

    @Test
    public void addDepartmentNewstoDepartmentAddsNewsCorrectly() throws Exception {

        Department testDepartment = setupDepartment();
        Department altDepartment = setupAltDepartment();

        departmentDao.add(testDepartment);
        departmentDao.add(altDepartment);

        DepartmentNews testDepartmentNews = setupNewDepartmentNews();

        departmentNewsDao.add(testDepartmentNews);

        departmentNewsDao.addDepartmentNewsToDepartment(testDepartmentNews, testDepartment);
        departmentNewsDao.addDepartmentNewsToDepartment(testDepartmentNews, altDepartment);

        assertEquals(2, departmentNewsDao.getAllDepartmentsForANewsDetail(testDepartmentNews.getId()).size());
    }



    @Test
    public void deleteingRestaurantAlsoUpdatesJoinTable() throws Exception {
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

    // helpers

    public DepartmentNews setupNewDepartmentNews(){
        return new DepartmentNews("Sushi");
    }

    public Department setupDepartment (){
        Department department = new Department("Fish Omena", "214 NE Safaricom", "97232", "254-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        departmentDao.add(department);
        return department;
    }

    public Department setupAltDepartment (){
        Department department = new Department("Fish Omena", "214 NE Safaricom", "97232", "254-402-9874");
        departmentDao.add(department);
        return department;
    }
}