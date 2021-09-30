package models;
import java.util.Objects;

public class DepartmentNews {
    private String name;
    private int id;

    public DepartmentNews(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentNews)) return false;
        DepartmentNews departmentNews = (DepartmentNews) o;
        return id == departmentNews.id &&
                Objects.equals(name, departmentNews.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
