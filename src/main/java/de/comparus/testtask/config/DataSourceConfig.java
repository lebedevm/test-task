package de.comparus.testtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("db")
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public List<String> dbNames(ApplicationProperties applicationProperties) {
        return applicationProperties.getDataSources().stream()
                .map(DbProperties::name)
                .toList();
    }

    @Bean
    public Map<String, DataSource> dataSources(ApplicationProperties applicationProperties) {
        return applicationProperties.getDataSources().stream()
                .collect(toMap(DbProperties::name, this::buildDataSource));
    }

    private DataSource buildDataSource(DbProperties dbProperties) {
        return DataSourceBuilder.create()
                .url(dbProperties.url())
                .username(dbProperties.user())
                .password(dbProperties.password())
                .build();
    }

    @Bean
    public Map<String, JdbcTemplate> jdbcTemplates(Map<String, DataSource> dataSources) {
        return dataSources.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> new JdbcTemplate(e.getValue())));
    }

    @Bean
    public Map<String, DbInfo> userQueries(ApplicationProperties applicationProperties) {
        return applicationProperties.getDataSources().stream()
                .collect(toMap(
                        DbProperties::name,
                        p -> new DbInfo(createUserQuery(p), p.mapping().id(), p.mapping().username())
                ));
    }

    private String createUserQuery(DbProperties p) {
        return "select "
                + p.mapping().id() + " as id, "
                + p.mapping().username() + " as username, "
                + p.mapping().name() + " as name, "
                + p.mapping().surname() + " as surname " +
                "from " + p.table();
    }

    @Bean
    public Set<String> numericDbNames(ApplicationProperties applicationProperties) {
        return applicationProperties.getDataSources().stream()
                .filter(p -> p.idType() != null && p.idType().equals("Number"))
                .map(DbProperties::name)
                .collect(toSet());
    }
}


