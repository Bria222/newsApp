package models.dao;

import models.Department;
import models.NewsDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;

public class Sql2ONewsDetailsDaoTest {
    private Connection conn;
    private Sql2ONewsDetailsDao newsDetailsDao;
    private Sql2ODepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        newsDetailsDao = new Sql2ONewsDetailsDao(sql2o);
        departmentDao = new Sql2ODepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

//    @Test
//    public void addingNewsDetailSetsId() throws Exception {
//        NewsDetails testNewsDetails = setupNewsDetails();
//        assertEquals(1, testNewsDetails.getId());
//    }


    @Test
    public void getAll() throws Exception {
        NewsDetails newsDetails1 = setup();
        NewsDetails newsDetails2 = setup();
        assertEquals(2, newsDetailsDao.getAll().size());
    }

    @Test
    public void getAllNewsDetailsByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment(); //add in some extra data to see if it interferes
        NewsDetails newsDetails1 = setupNewsDetailsForDepartment(testDepartment);
        NewsDetails newsDetails2 = setupNewsDetailsForDepartment(testDepartment);
        NewsDetails newsDetailsForOtherRestaurant = setupNewsDetailsForDepartment(otherDepartment);
        assertEquals(2, newsDetailsDao.getAllNewsDetailsByDepartment(testDepartment.getId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        NewsDetails testNewsDetails = setup();
        NewsDetails otherNewsDetails = setup();
        assertEquals(2, newsDetailsDao.getAll().size());
        newsDetailsDao.deleteById(testNewsDetails.getId());
        assertEquals(1, newsDetailsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        NewsDetails testNewsDetails = setup();
        NewsDetails otherNewsDetails = setup();
        newsDetailsDao.clearAll();
        assertEquals(0, newsDetailsDao.getAll().size());
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        NewsDetails testNewsDetails = new NewsDetails("Captain Kirk", "foodcoma!", 3, testDepartment.getId());
        newsDetailsDao.add(testNewsDetails);

        long creationTime = testNewsDetails.getCreatedat();
        long savedTime = newsDetailsDao.getAll().get(0).getCreatedat();
        //assertEquals(creationTime, reviewDao.getAll().get(0).getCreatedat());
        String formattedCreationTime = testNewsDetails.getFormattedCreatedAt();
        String formattedSavedTime = newsDetailsDao.getAll().get(0).getFormattedCreatedAt();
        assertEquals(formattedCreationTime,formattedSavedTime);
        assertEquals(creationTime, savedTime);
    }

    @Test
    public void reviewsAreReturnedInCorrectOrder() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        NewsDetails testNewsDetails = new NewsDetails("Captain Kirk", "foodcoma!",3,  testDepartment.getId());
        newsDetailsDao.add(testNewsDetails);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        NewsDetails testSecondNewsDetails = new NewsDetails("Mr. Spock", "passable",1,  testDepartment.getId());
        newsDetailsDao.add(testSecondNewsDetails);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        NewsDetails testThirdNewsDetails = new NewsDetails("Scotty", "bloody good grub!",4,  testDepartment.getId());
        newsDetailsDao.add(testThirdNewsDetails);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        NewsDetails testFourthNewsDetails = new NewsDetails("Mr. Sulu", "I prefer home cooking",2,  testDepartment.getId());
        newsDetailsDao.add(testFourthNewsDetails);

        assertEquals(4, newsDetailsDao.getAllNewsDetailsByDepartment(testDepartment.getId()).size()); //it is important we verify that the list is the same size.
        assertEquals("I prefer home cooking", newsDetailsDao.getAllNewsDetailsByDepartmentSortedNewestToOldest(testDepartment.getId()).get(0).getContent());
    }
    //helpers

    public NewsDetails setup() {
        NewsDetails newsDetails = new NewsDetails("great", "Kim", 4, 555);
        newsDetailsDao.add(newsDetails);
        return newsDetails;
    }

    public NewsDetails setupNewsDetailsForDepartment(Department department) {
        NewsDetails newsDetails = new NewsDetails("great", "Kim", 4, department.getId());
        newsDetailsDao.add(newsDetails);
        return newsDetails;
    }

    public Department setupDepartment() {
        Department department = new Department("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        departmentDao.add(department);
        return department;
    }
}