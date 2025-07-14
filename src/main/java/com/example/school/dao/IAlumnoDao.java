package com.example.school.dao;

import com.example.school.model.Alumno;

import java.util.List;

public interface IAlumnoDao {

    List<Alumno> findAll() throws Exception;
    Alumno  findById(int id) throws Exception;
    void    create(Alumno a) throws Exception;
    void    update(Alumno a) throws Exception;
    void    delete(int id) throws Exception;
     void updatePhotoPath(int id, String path) throws Exception;
}
