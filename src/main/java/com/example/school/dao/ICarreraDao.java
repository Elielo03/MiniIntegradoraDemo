package com.example.school.dao;

import com.example.school.model.Carrera;

import java.util.List;

public interface ICarreraDao {

    public List<Carrera> getCarreras() throws Exception;
    public Carrera getCarrera(int id) throws Exception;
    public void addCarrera(Carrera carrera) throws Exception;
    public void updateCarrera(Carrera carrera) throws Exception;
    public void deleteCarrera(Carrera carrera) throws Exception;
}
