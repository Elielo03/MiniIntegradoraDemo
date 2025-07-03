package com.example.school.controller;

import com.example.school.dao.impl.AlumnoDao;
import com.example.school.dao.impl.CarreraDao;
import com.example.school.model.Alumno;
import com.example.school.model.Carrera;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AlumnoFormController {

    @FXML
    TextField txtNombres;
    @FXML
    TextField txtApellidos;
    @FXML
    TextField txtCorreo;
    @FXML
    DatePicker dpFechaNacimiento;

    @FXML private ComboBox<Carrera> cbCarreras;
    @FXML
    Button btnSubmit;
    @FXML
    Button btnCancel;

    private final AlumnoDao alumnoDao = new AlumnoDao();
    private final CarreraDao carreraDao = new CarreraDao();

    @FXML
    public void initialize(){
        try {
            List<Carrera> carreras=carreraDao.getCarreras();
            cbCarreras.setItems(FXCollections.observableList(carreras));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error en DB" ,"Error al cargar las carreras");
        }
    }

    @FXML
    private void onSubmit() {
        String nombre = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        Carrera carrera = cbCarreras.getSelectionModel().getSelectedItem();
        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty()
                || fechaNacimiento == null || carrera == null) {
            showAlert("Faltan datos", "Por favor completa todos los campos.");
            return;
        }
            try{
                Alumno alumno = new Alumno();
                alumno.setNombre(nombre);
                alumno.setApellidos(apellidos);
                alumno.setCorreo(correo);
                alumno.setFechaNacimiento(fechaNacimiento);
                alumno.setIdCarrera(carrera.getId());
                alumnoDao.create(alumno);
                showAlert("Exito", "El alumno ha sido guardado correctamente");
                closeWindow();
            }catch(Exception e){
                showAlert("Error al crear alumno", "Error al crear alumno");
            }

        }



    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }



    public void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
