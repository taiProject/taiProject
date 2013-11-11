package pl.edu.agh.dfs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import pl.edu.agh.dfs.models.User;

public class UserResultSetExtractor implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		User user = new User();
		user.setLogin(resultSet.getString(1));
		user.setPassword(resultSet.getString(2));
		user.setRoleName(resultSet.getString(3));
		return user;
	}

}
