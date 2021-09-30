package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("Fish Witch", testDepartment.getName());
    }

    @Test
    public void getAddressReturnsCorrectAddress() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("214 NE Broadway", testDepartment.getCompany());
    }

    @Test
    public void getZipReturnsCorrectZip() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("97232", testDepartment.getDivision());
    }
    @Test
    public void getPhoneReturnsCorrectPhone() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("503-402-9874", testDepartment.getPhone());
    }

    @Test
    public void getWebsiteReturnsCorrectWebsite() throws Exception {
        Department testDepartment = setupAltRestaurant();
        assertEquals("no website listed", testDepartment.getWebsite());
    }

    @Test
    public void getEmailReturnsCorrectEmail() throws Exception {
        Department testDepartment = setupAltRestaurant();
        assertEquals("no email available", testDepartment.getEmail());
    }

    @Test
    public void setNameSetsCorrectName() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setName("Steak House");
        assertNotEquals("Fish Witch", testDepartment.getName());
    }

    @Test
    public void setAddressSetsCorrectAddress() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setCompany("6600 NE Ainsworth");
        assertNotEquals("214 NE Broadway", testDepartment.getCompany());
    }

    @Test
    public void setZipSetsCorrectZip() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setDivision("78902");
        assertNotEquals("97232", testDepartment.getDivision());
    }
    @Test
    public void setPhoneSetsCorrectPhone() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setPhone("971-898-7878");
        assertNotEquals("503-402-9874", testDepartment.getPhone());
    }

    @Test
    public void setWebsiteSetsCorrectWebsite() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setWebsite("http://steakhouse.com");
        assertNotEquals("http://fishwitch.com", testDepartment.getWebsite());
    }

    @Test
    public void setEmailSetsCorrectEmail() throws Exception {
        Department testDepartment = setupRestaurant();
        testDepartment.setEmail("steak@steakhouse.com");
        assertNotEquals("hellofishy@fishwitch.com", testDepartment.getEmail());
    }

    public Department setupRestaurant (){
        return new Department("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
    }

    public Department setupAltRestaurant (){
        return new Department("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
    }
}