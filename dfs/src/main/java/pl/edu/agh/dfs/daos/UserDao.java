package pl.edu.agh.dfs.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import pl.edu.agh.dfs.mapper.UserRowMapper;
import pl.edu.agh.dfs.models.Role;
import pl.edu.agh.dfs.models.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao{
    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(String login, String password, Role role) {
        JdbcTemplate insert = new JdbcTemplate(dataSource);
        insert.update("INSERT INTO USERS (login, password, role_name) VALUES(?, ?, ?)", new Object[] {login, password, role.toString()});
    }

    @Override
    public User select(String login) {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        ArrayList<User> users = (ArrayList<User>) select.query("SELECT LOGIN, PASSWORD, ROLE_NAME from USERS where LOGIN=?", new Object[]{login}, new UserRowMapper());
        if(!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> selectAll() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("SELECT LOGIN, PASSWORD, ROLE_NAME from USERS", new UserRowMapper());
    }

    @Override
    public void delete(String login) {
        JdbcTemplate delete = new JdbcTemplate(dataSource);
        delete.update("DELETE from USERS where LOGIN=?", new Object[]{login});
    }

    @Override
    public void deleteAll() {
        JdbcTemplate delete = new JdbcTemplate(dataSource);
        delete.update("DELETE from USERS");
    }
}
