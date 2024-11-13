package de.comparus.testtask.model;

import de.comparus.testtask.config.DbInfo;

public enum QueryCondition {

    BY_USERNAME {
        @Override
        public String create(DbInfo dbInfo, String parameter) {
            return " where " + dbInfo.usernameField() + " like '%" + parameter + "%'";
        }
    },

    BY_ID {
        @Override
        public String create(DbInfo dbInfo, String parameter) {
            return " where " + dbInfo.idField() + " = '" + parameter + "'";
        }
    };

    public abstract String create(DbInfo dbInfo, String parameter);
}
