package de.comparus.testtask.config;

public record DbInfo(
        String query,
        String idField,
        String usernameField
) {
}
