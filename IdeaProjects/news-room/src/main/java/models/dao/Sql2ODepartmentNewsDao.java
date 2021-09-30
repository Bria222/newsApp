package models.dao;

import models.DepartmentNews;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2ODepartmentNewsDao implements DepartmentNewsDao {
    private final Sql2o sql2o;
    public Sql2ODepartmentNewsDao(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(DepartmentNews departmentNews) {
        String sql = "INSERT INTO departmentnews (name) VALUES (:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(departmentNews)
                    .executeUpdate()
                    .getKey();
            departmentNews.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<DepartmentNews> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM  departmentnews")
                    .executeAndFetch(DepartmentNews.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from  departmentnews WHERE id=:id";
        String deleteJoin = "DELETE from departments_departmentnews WHERE departmentnewsid = :departmentnewsId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(deleteJoin)
                    .addParameter(" departmentnewsId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departmentnews";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentNewsToDepartment(DepartmentNews departmentNews, Department department){
        String sql = "INSERT INTO departments_departmentnews (departmentid, departmentnewsid) VALUES (:departmentId, :departmentnewsId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("departmentnewsId", departmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAllDepartmentsForANewsDetail(int departmentnewsId) {
        List<Department> departments = new ArrayList();
        String joinQuery = "SELECT departmentId FROM departments_departmentnews WHERE departmentnewsid = :departmentnewsId";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentsIds = con.createQuery(joinQuery)
                    .addParameter("departmentnewsId", departmentnewsId)
                    .executeAndFetch(Integer.class);
            for (Integer departmentId : allDepartmentsIds){
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId";
                departments.add(
                        con.createQuery(departmentQuery)
                                .addParameter("departmentId", departmentId)
                                .executeAndFetchFirst(Department.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public DepartmentNews findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departmentnews WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(DepartmentNews.class);
        }
    }

}
