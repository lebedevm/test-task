package de.comparus.testtask.model;

import java.util.Set;
import java.util.function.Predicate;

public enum IdType {
    TEXT {
        @Override
        public Predicate<String> limit(Set<String> numericDbNames) {
            return db -> !numericDbNames.contains(db);
        }
    },
    NUMERIC {
        @Override
        public Predicate<String> limit(Set<String> numericDbNames) {
            return db -> true;
        }
    };

    public static IdType getIdType(String id) {
        return id.matches("[0-9]+") ? IdType.NUMERIC : IdType.TEXT;
    }

    public abstract Predicate<String> limit(Set<String> numericDbNames);
}
