package models.dao;

import models.NewsDetails;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.Collection;
import java.util.List;

public class Sql2ONewsDetailsDao implements NewsDetailsDao {
    private final Sql2o sql2o;
    public Sql2ONewsDetailsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(NewsDetails newsDetails) {
        String sql = "INSERT INTO newsdetails (writtenby, content, rating, departmentid, createdat) VALUES (:writtenBy, :content, :rating, :departmentId, :createdat)"; //if you change your model, be sure to update here as well!
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(newsDetails)
                    .executeUpdate()
                    .getKey();
            newsDetails.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<NewsDetails> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM newsdetails")
                    .executeAndFetch(NewsDetails.class);
        }
    }

    @Override
     public List<NewsDetails> getAllNewsDetailsByDepartment(int departmentId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM newsdetails WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(NewsDetails.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from newsdetails WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from newsdetails";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<NewsDetails> getAllNewsDetailsByDepartmentSortedNewestToOldest(int departmentId) {

        List<NewsDetails> unsortedNewsDetails = getAllNewsDetailsByDepartment(departmentId); //calling other method!
        List<NewsDetails> sortedNewsDetails = unsortedNewsDetails;

        return sortedNewsDetails;
    }


}
