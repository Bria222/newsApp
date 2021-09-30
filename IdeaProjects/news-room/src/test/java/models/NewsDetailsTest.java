package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsDetailsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContent() {
        NewsDetails testNewsDetails = setupReview();
        assertEquals("Great service", testNewsDetails.getContent());
    }

    @Test
    public void setContent() {
        NewsDetails testNewsDetails = setupReview();
        testNewsDetails.setContent("No free dessert :(");
        assertNotEquals("Great service", testNewsDetails.getContent());
    }

    @Test
    public void getWrittenBy() {
        NewsDetails testNewsDetails = setupReview();
        assertEquals("Kim", testNewsDetails.getWrittenBy());
    }

    @Test
    public void setWrittenBy() {
        NewsDetails testNewsDetails = setupReview();
        testNewsDetails.setWrittenBy("Mike");
        assertNotEquals("Kim", testNewsDetails.getWrittenBy());
    }

    @Test
    public void getRating() {
        NewsDetails testNewsDetails = setupReview();
        assertEquals(4, testNewsDetails.getRating());
    }

    @Test
    public void setRating() {
        NewsDetails testNewsDetails = setupReview();
        testNewsDetails.setRating(1);
        assertNotEquals(4, testNewsDetails.getRating());
    }

    @Test
    public void getRestaurantId() {
        NewsDetails testNewsDetails = setupReview();
        assertEquals(1, testNewsDetails.getDepartmentId());
    }

    @Test
    public void setRestaurantId() {
        NewsDetails testNewsDetails = setupReview();
        testNewsDetails.setDepartmentId(10);
        assertNotEquals(1, testNewsDetails.getDepartmentId());
    }

    @Test
    public void setId() {
        NewsDetails testNewsDetails = setupReview();
        testNewsDetails.setId(5);
        assertEquals(5, testNewsDetails.getId());
    }


    // helper
    public NewsDetails setupReview (){
        return new NewsDetails("Great service", "Kim", 4, 1);
    }
}