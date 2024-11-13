package de.comparus.testtask.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationProperties {
    private List<DbProperties> dataSources;
}

record DbProperties(
        String name,
        String suffix,
        String idType,
        String strategy,
        String url,
        String table,
        String user,
        String password,
        Mapping mapping
) {
}

record Mapping(
        String id,
        String username,
        String name,
        String surname
) {
}
