package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentNewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() {
        DepartmentNews testDepartmentNews = setupFoodtype();
        assertEquals("dessert", testDepartmentNews.getName());
    }

    @Test
    public void setName() {
        DepartmentNews testDepartmentNews = setupFoodtype();
        testDepartmentNews.setName("breakfast");
        assertNotEquals("dessert", testDepartmentNews.getName());
    }

    @Test
    public void setId() {
        DepartmentNews testDepartmentNews = setupFoodtype();
        testDepartmentNews.setId(5);
        assertEquals(5, testDepartmentNews.getId());
    }

    // helper
    public DepartmentNews setupFoodtype(){
        return new DepartmentNews("dessert");
    }
}
