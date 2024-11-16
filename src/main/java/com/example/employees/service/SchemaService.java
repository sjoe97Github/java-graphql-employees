// src/main/java/com/example/employees/service/SchemaService.java
package com.example.employees.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SchemaService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getTables() throws Exception {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'employees' AND table_type != 'VIEW'";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public String getTableDDL(String tableName) throws Exception {
        String sql = "SHOW CREATE TABLE " + tableName;
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                String tableDDL = rs.getString("Create Table");
//                String tableDDL = rs.getString(2);
                return tableDDL;
            }
            return null;
        });
    }

    public String getIndexesDDL(String tableName) throws Exception {
        String sql = "SHOW INDEX FROM " + tableName;
        List<Map<String, Object>> indexes = jdbcTemplate.queryForList(sql);
        StringBuilder indexesDDL = new StringBuilder();
        for (Map<String, Object> index : indexes) {
            indexesDDL.append(index.get("Key_name")).append(" (")
                    .append(index.get("Column_name")).append(");\n");
        }
        return indexesDDL.toString();
    }

    public String getForeignKeysDDL(String tableName) throws Exception {
        String sql = "SELECT CONSTRAINT_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME " +
                "FROM information_schema.KEY_COLUMN_USAGE " +
                "WHERE TABLE_SCHEMA = 'employees' AND TABLE_NAME = ? AND REFERENCED_TABLE_NAME IS NOT NULL";
        List<Map<String, Object>> foreignKeys = jdbcTemplate.queryForList(sql, tableName);
        StringBuilder foreignKeysDDL = new StringBuilder();
        for (Map<String, Object> foreignKey : foreignKeys) {
            foreignKeysDDL.append("ALTER TABLE ").append(tableName)
                    .append(" ADD CONSTRAINT ").append(foreignKey.get("CONSTRAINT_NAME"))
                    .append(" FOREIGN KEY (").append(foreignKey.get("COLUMN_NAME"))
                    .append(") REFERENCES ").append(foreignKey.get("REFERENCED_TABLE_NAME"))
                    .append(" (").append(foreignKey.get("REFERENCED_COLUMN_NAME")).append(");\n");
        }
        return foreignKeysDDL.toString();
    }

    public String getConstraintsDDL(String tableName) throws Exception {
        String sql = "SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE " +
                "FROM information_schema.TABLE_CONSTRAINTS " +
                "WHERE TABLE_SCHEMA = 'employees' AND TABLE_NAME = ?";
        List<Map<String, Object>> constraints = jdbcTemplate.queryForList(sql, tableName);
        StringBuilder constraintsDDL = new StringBuilder();
        for (Map<String, Object> constraint : constraints) {
            constraintsDDL.append("ALTER TABLE ").append(tableName)
                    .append(" ADD CONSTRAINT ").append(constraint.get("CONSTRAINT_NAME"))
                    .append(" ").append(constraint.get("CONSTRAINT_TYPE")).append(";\n");
        }
        return constraintsDDL.toString();
    }

    public String getSchemaDDL() throws Exception {
        List<String> tables = getTables();
        StringBuilder schemaDDL = new StringBuilder();
        for (String table : tables) {
            schemaDDL.append(getTableDDL(table)).append(";\n\n")
                    .append(getIndexesDDL(table)).append("\n")
                    .append(getForeignKeysDDL(table)).append("\n")
                    .append(getConstraintsDDL(table)).append("\n");
        }
        return schemaDDL.toString();
    }
}