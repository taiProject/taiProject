package pl.edu.agh.dfs.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pl.edu.agh.dfs.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultSetExtractor implements ResultSetExtractor{
    @Override
    public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        User user = new User();
        user.setLogin(resultSet.getString(1));
        user.setPassword(resultSet.getString(2));
        user.setRoleName(resultSet.getString(3));
        return user;
    }
}
