package com.example.school.dao.impl;

import com.example.school.config.DBConnection;
import com.example.school.config.DBConnectionSqlite;
import com.example.school.dao.IUsuarioDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao implements IUsuarioDao {


    @Override
    public boolean login(String username, String password) throws Exception {
        final String sql = "SELECT 1 FROM usuario WHERE correo = ? AND pass = ?";
        try (Connection con = DBConnectionSqlite.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (org.sqlite.SQLiteException e) {

            throw new RuntimeException("La tabla 'usuario' no existe en el archivo .db abierto. "
                    + "Verifica la ruta/nombre del archivo y que sea el school_exam.db correcto.", e);
        }

    }
}
