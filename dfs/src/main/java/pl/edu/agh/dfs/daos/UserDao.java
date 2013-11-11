package pl.edu.agh.dfs.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import pl.edu.agh.dfs.mapper.UserRowMapper;
import pl.edu.agh.dfs.models.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao{
    private DataSource dataSource;
    private JdbcTemplate template;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(String login, String password, String role) {
        template.update("INSERT INTO USERS (login, password, role_name) VALUES(?, ?, ?)", new Object[] {login, password, role});
    }

    @Override
    public void update(String login, String password, String role) {
        template.update("UPDATE USERS set password=?, role_name=? where login=?", password, role, login);
    }

    @Override
    public User select(String login) {
        ArrayList<User> users = (ArrayList<User>) template.query("SELECT LOGIN, PASSWORD, ROLE_NAME from USERS where LOGIN=?", new Object[]{login}, new UserRowMapper());
        if(!users.isEmpty()){
            return users.get(0);
        }
        return null;
    }

    @Override
    public List<User> selectAll() {
        return template.query("SELECT LOGIN, PASSWORD, ROLE_NAME from USERS", new UserRowMapper());
    }

    @Override
    public void delete(String login) {
        template.update("DELETE from USERS where LOGIN=?", new Object[]{login});
    }

    @Override
    public void deleteAll() {
        template.update("DELETE from USERS");
    }
}
