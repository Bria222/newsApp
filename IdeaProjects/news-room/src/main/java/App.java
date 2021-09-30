import com.google.gson.Gson;
import exceptions.ApiException;
import models.DepartmentNews;
import models.Department;
import models.NewsDetails;
import models.dao.Sql2ODepartmentDao;
import models.dao.Sql2ODepartmentNewsDao;
import models.dao.Sql2ONewsDetailsDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2ODepartmentNewsDao departmentNewsDao;
        Sql2ODepartmentDao departmentDao;
        Sql2ONewsDetailsDao newsDetailsDao;
       Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        departmentDao = new Sql2ODepartmentDao(sql2o);
        departmentNewsDao = new Sql2ODepartmentNewsDao(sql2o);
        newsDetailsDao = new Sql2ONewsDetailsDao(sql2o);
        conn = sql2o.open();

        //ROUTES
//        get("/departments"
//        get("/departments/:id"
//        post("/departments/new"
//        post("/departmentnews/new"
//        get("/departmentnews"
//        get("/departments/:id/newsdetails"
//        post("/departments/:departmentId/newsdetails/new

        //CREATE
        post("/departments/new", "application/json", (req, res) -> {
            Department restaurant = gson.fromJson(req.body(), Department.class);
            departmentDao.add(restaurant);
            res.status(201);
            return gson.toJson(restaurant);
        });

        //READ
        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departmentDao.getAll());
        });

        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentDao.findById(departmentId));
        });

        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });


        //CREATE
        post("/departments/:departmentId/departmentnews/:departmentnewsId", "application/json", (req, res) -> {

            int departmentId = Integer.parseInt(req.params("departmentId"));
            int departmentNewsId = Integer.parseInt(req.params("departmentNewsId"));
            Department department= departmentDao.findById(departmentId);
            DepartmentNews departmentNews = departmentNewsDao.findById(departmentNewsId);


            if (department != null && departmentNews != null){
                departmentNewsDao.addDepartmentNewsToDepartment(departmentNews, department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and DepartmentNews'%s' have been associated",departmentNews.getName(), department.getName()));
            }
            else {
                throw new ApiException(404, String.format("Department or Department News does not exist"));
            }
        });

        get("/departments/:id/departmentnews", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllDepartmentNewsByDepartment(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no department news are listed for this restaurant.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllDepartmentNewsByDepartment(departmentId));
            }
        });

        get("/departmentnews/:id/departments", "application/json", (req, res) -> {
            int departmentNewsId = Integer.parseInt(req.params("id"));
            DepartmentNews departmentNewsToFind = departmentNewsDao.findById(departmentNewsId);
            if (departmentNewsToFind == null){
                throw new ApiException(404, String.format("No department news with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentNewsDao.getAllDepartmentsForANewsDetail(departmentNewsId).size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this department news.\"}";
            }
            else {
                return gson.toJson(departmentNewsDao.getAllDepartmentsForANewsDetail(departmentNewsId));
            }
        });


        post("/departments/:departmentId/newsdetails/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            NewsDetails review = gson.fromJson(req.body(), NewsDetails.class);

            review.setDepartmentId(departmentId);
            newsDetailsDao.add(review);
            res.status(201);
            return gson.toJson(review);
        });

        post("/departmentnews/new", "application/json", (req, res) -> {
            NewsDetails newsdetails = gson.fromJson(req.body(), NewsDetails.class);
            newsDetailsDao.add(newsdetails);
            res.status(201);
            return gson.toJson(newsdetails);
        });

        //READ
        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int restaurantId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(restaurantId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        get("/departments/:id/newsdetails", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<NewsDetails> allNewsDetails;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNewsDetails = newsDetailsDao.getAllNewsDetailsByDepartment(departmentId);

            return gson.toJson(allNewsDetails);
        });

        get("/departmentnews", "application/json", (req, res) -> {
            return gson.toJson(newsDetailsDao.getAll());
        });

        post("/departments/:departmentId/newsdetail/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            NewsDetails newsdetail = gson.fromJson(req.body(), NewsDetails.class);
            newsdetail.setCreatedat(); //I am new!
            newsdetail.setFormattedCreatedAt();
            newsdetail.setDepartmentId(departmentId);
            newsDetailsDao.add(newsdetail);
            res.status(201);
            return gson.toJson(newsdetail);
        });
        get("/departments/:id/sortedNewsDetails", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted reviews or unsorted ones.
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            List<NewsDetails> allNewsDetails;
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allNewsDetails = newsDetailsDao.getAllNewsDetailsByDepartmentSortedNewestToOldest(departmentId);
            return gson.toJson(allNewsDetails);
        });
        //CREATE
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });


        //FILTERS
        exception(ApiException.class, (exc, req, res) -> {
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });

    }
    }
