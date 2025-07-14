package com.example.school.dao.impl;

import com.example.school.config.DBConnection;
import com.example.school.dao.IAlumnoDao;
import com.example.school.model.Alumno;
import com.example.school.model.Asignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao implements IAlumnoDao {
    @Override
    public List<Alumno> findAll() throws Exception {
        String sql = "select * from alumno";
        List<Alumno> alumnos = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));

                alumno.setNombre(rs.getString("nombres"));
                alumno.setApellidos(rs.getString("apellidos"));
                alumno.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setIdCarrera(rs.getInt("id_carrera"));
                alumno.setCorreo(rs.getString("correo"));
                alumno.setAsignaturas(getAsignaturasByAlumno(alumno));
                System.out.println(alumno.getAsignaturas().size());
                alumnos.add(alumno);
            }
            return alumnos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }


    }
    @Override
    public Alumno findById(int id) throws Exception {
        String sql = "select * from alumno where id = ?";
        Alumno alumno = new Alumno();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombres"));
                alumno.setApellidos(rs.getString("apellidos"));
                alumno.setCorreo(rs.getString("correo"));
                alumno.setIdCarrera(rs.getInt("id_carrera"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return alumno;
    }

    @Override
    public void create(Alumno a) throws Exception {
        String sql = "INSERT INTO alumno (nombres, apellidos, fecha_nacimiento, correo, id_carrera) "
                + "VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellidos());
            ps.setDate(3, java.sql.Date.valueOf(a.getFechaNacimiento()));
            ps.setString(4, a.getCorreo());
            ps.setInt(5, a.getIdCarrera());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Alumno a) throws Exception {
        String sql = "UPDATE alumno SET nombres=?, apellidos=?, fecha_nacimiento=?, correo=?, id_carrera=? "
                + "WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellidos());
            ps.setDate(3, java.sql.Date.valueOf(a.getFechaNacimiento()));
            ps.setString(4, a.getCorreo());
            ps.setInt(5, a.getIdCarrera());
            ps.setInt(6, a.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM alumno WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updatePhotoPath(int id, String path) throws Exception {
        String sql = "UPDATE alumno SET path=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, path);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Asignatura> getAsignaturasByAlumno(Alumno alumno) throws Exception {
        String sql = "select asig.id, asig.nombre, asig.descripcion from asignatura asig  " +
                " JOIN alumno_asignatura a on a.id_asignatura = asig.id where id_alumno = ?";
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Asignatura asignatura = new Asignatura();
                asignatura.setId(rs.getInt("id"));
                asignatura.setNombre(rs.getString("nombre"));
                asignatura.setDescripcion(rs.getString("descripcion"));
                asignaturas.add(asignatura);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return asignaturas;


}

    public static void main(String[] args) {
        AlumnoDao alumnoDao = new AlumnoDao();
        try {
            List<Alumno> alumnos= alumnoDao.findAll();
            System.out.println(alumnos.iterator().next().getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
