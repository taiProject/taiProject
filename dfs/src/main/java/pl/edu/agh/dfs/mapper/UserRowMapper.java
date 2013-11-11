package pl.edu.agh.dfs.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.edu.agh.dfs.models.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		UserResultSetExtractor extractor = new UserResultSetExtractor();
		return extractor.extractData(resultSet);
	}
}
