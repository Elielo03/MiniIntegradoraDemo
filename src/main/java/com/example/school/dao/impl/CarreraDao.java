package com.example.school.dao.impl;

import com.example.school.config.DBConnection;
import com.example.school.dao.ICarreraDao;
import com.example.school.model.Carrera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarreraDao implements ICarreraDao {

    @Override
    public List<Carrera> getCarreras() throws Exception {
        String sql = "select * from carrera";
        List<Carrera> carreras = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Carrera carrera = new Carrera();
                carrera.setId(rs.getInt("id"));
                carrera.setNombre(rs.getString("nombre"));
                carrera.setDescripcion(rs.getString("descripcion"));

                carreras.add(carrera);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return carreras;
    }

    @Override
    public Carrera getCarrera(int id) throws Exception {
        String sql="select * from carrera where id=?";
        Carrera carrera = new Carrera();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                carrera.setId(rs.getInt("id"));
                carrera.setNombre(rs.getString("nombre"));
                carrera.setDescripcion(rs.getString("descripcion"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return carrera;
    }

    @Override
    public void addCarrera(Carrera carrera) throws Exception {
        String sql = "insert into carrera (nombre,descripcion) values (?,?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, carrera.getNombre());
            ps.setString(2, carrera.getDescripcion());
            ps.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCarrera(Carrera carrera) throws Exception {
        String sql = "update  carrera set  nombre=?, descripcion=? where id=?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, carrera.getNombre());
            ps.setString(2, carrera.getDescripcion());
            ps.setInt(3, carrera.getId());
            ps.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteCarrera(Carrera carrera) throws Exception {

    }
}
