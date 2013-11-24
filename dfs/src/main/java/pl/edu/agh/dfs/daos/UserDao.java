package pl.edu.agh.dfs.daos;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import pl.edu.agh.dfs.mapper.UserRowMapper;
import pl.edu.agh.dfs.models.User;

public class UserDao implements IDao {
	private JdbcTemplate template;

	@Override
	public void setDataSource(DataSource dataSource) {
		template = new JdbcTemplate(dataSource);
	}

    /**
     * @param login login of newly created user
     * @param password password of newly created user
     * @param role role in system of newly created user
     */
	@Override
	public void create(String login, String password, String role) {
		template.update("INSERT INTO USERS (login, password, role_name) VALUES(?, ?, ?)", new Object[] { login,
				password, role });
	}

    /**
     * @param login new login to update
     * @param password new password to update
     * @param role new role to update
     */
	@Override
	public void update(String login, String password, String role) {
		template.update("UPDATE USERS set password=?, role_name=? where login=?", password, role, login);
	}

    /**
     * @param login login of user to select from database
     * @return user from database
     */
	@Override
	public User select(String login) {
		ArrayList<User> users = (ArrayList<User>) template.query(
				"SELECT LOGIN, PASSWORD, ROLE_NAME from USERS where LOGIN=?", new Object[] { login },
				new UserRowMapper());
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

    /**
     * @return list of all users in database
     */
	@Override
	public List<User> selectAll() {
		return template.query("SELECT LOGIN, PASSWORD, ROLE_NAME from USERS", new UserRowMapper());
	}

    /**
     * @param login login of the user to be deleted
     */
	@Override
	public void delete(String login) {
		template.update("DELETE from USERS where LOGIN=?", new Object[] { login });
	}

    /**
     * delete all users in database
     */
	@Override
	public void deleteAll() {
		template.update("DELETE from USERS");
	}
}
