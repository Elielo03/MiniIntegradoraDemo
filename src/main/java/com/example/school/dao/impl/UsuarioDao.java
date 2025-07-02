package com.example.school.dao.impl;

import com.example.school.config.DBConnection;
import com.example.school.dao.IUsuarioDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDao implements IUsuarioDao {


    @Override
    public boolean login(String username, String password) throws Exception {
        String sql = "select * from usuario where correo = ? and pass = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
