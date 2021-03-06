package pl.edu.agh.dfs.daos;

import pl.edu.agh.dfs.models.User;

import javax.sql.DataSource;

import java.util.List;

public interface IDao {
    void setDataSource(DataSource dataSource);
    void create(String login, String password, String role);
    void update(String login, String password, String role);
    User select(String login);
    List<User> selectAll();
    void delete(String login);
    void deleteAll();
}
