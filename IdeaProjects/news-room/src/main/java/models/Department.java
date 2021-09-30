package models;

import java.util.Objects;

public class Department {

    private String name;
    private String company;
    private String division;
    private String phone;
    private String website;
    private String email;
    private int id;

    public Department(String name, String company, String division, String phone) {
        this.name = name;
        this.company = company;
        this.division = division;
        this.phone = phone;
        this.website = "no website listed";
        this.email = "no email available";
    }

    public Department(String name, String company, String division, String phone, String website, String email) {
        this.name = name;
        this.company = company;
        this.division = division;
        this.phone = phone;
        this.website = website;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getDivision() {
        return division;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(company, that.company) &&
                Objects.equals(division, that.division) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(website, that.website) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, division, phone, website, email, id);
    }
}