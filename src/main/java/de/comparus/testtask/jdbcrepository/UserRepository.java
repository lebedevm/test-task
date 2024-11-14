package de.comparus.testtask.jdbcrepository;

import de.comparus.testtask.config.DbInfo;
import de.comparus.testtask.jdbcmapper.UserMapper;
import de.comparus.testtask.model.QueryCondition;
import de.comparus.testtask.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Map<String, JdbcTemplate> jdbcTemplates;
    private final Map<String, DbInfo> userQueries;
    private final UserMapper userMapper = new UserMapper();

    public List<User> selectData(String dbName, String parameter, QueryCondition condition) {
        var jdbcTemplate = jdbcTemplates.get(dbName);
        var query = createQuery(dbName, parameter, condition);
        return jdbcTemplate.query(query, userMapper);
    }

    private String createQuery(String dbName, String parameter, QueryCondition condition) {
        var dbInfo = userQueries.get(dbName);
        String basicQuery = dbInfo.query();
        return isNotBlank(parameter)
                ? basicQuery + condition.create(dbInfo, parameter)
                : basicQuery;
    }
}
