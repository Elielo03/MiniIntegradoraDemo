package com.example.school.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSqlite {
    private static final String DB_PATH =
            Paths.get(System.getProperty("user.dir"), "db", "school_exam.db").toString();
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() throws SQLException {
        try { Class.forName("org.sqlite.JDBC"); }
        catch (ClassNotFoundException e) { throw new SQLException("Falta el driver SQLite.", e); }

        // DEBUG: confirma qué archivo se abre
        System.out.println("[SQLite] db path = " + DB_PATH);
        System.out.println("[SQLite] exists? " + Files.exists(Path.of(DB_PATH)));

        Connection con = DriverManager.getConnection(URL);
        con.createStatement().execute("PRAGMA foreign_keys=ON");
        return con;
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("¡Conexión exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}