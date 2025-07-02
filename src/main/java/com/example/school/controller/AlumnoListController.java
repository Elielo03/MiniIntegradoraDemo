package com.example.school.controller;

import com.example.school.dao.impl.AlumnoDao;
import com.example.school.model.Alumno;
import com.example.school.model.Asignatura;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class AlumnoListController implements Initializable {
    @FXML
    private TableView<Alumno> tableAlumnos;
    @FXML private TableColumn<Alumno, String> colId;
    @FXML private TableColumn<Alumno, String>  colNombre;
    @FXML private TableColumn<Alumno, String>  colApellidos;
    @FXML private TableColumn<Alumno, String>  colFecha;
    @FXML private TableColumn<Alumno, String>  colCorreo;
    @FXML private TableColumn<Alumno, String>  colCarrera;
    @FXML private TableColumn<Alumno, String>  colAsignaturas;
    @FXML private TableColumn<Alumno, Void>    colAcciones;

    AlumnoDao alumnoDao = new AlumnoDao();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asigna factories a las columnas estándar
        colId.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId()))
        );
        colNombre.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getNombre())
        );
        colApellidos.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getApellidos())
        );
        colFecha.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue()
                        .getFechaNacimiento().format(df))
        );
        colCorreo.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getCorreo())
        );
        colCarrera.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getIdCarrera()+"")
        );

        // Columna de asignaturas: junta nombres con comas
        colAsignaturas.setCellValueFactory(data -> {
            List<Asignatura> lista = data.getValue().getAsignaturas();
            String texto = String.join(", ",
                    lista.stream().map(Asignatura::getNombre).toList()
            );
            return new ReadOnlyStringWrapper(texto);
        });

        // Column “Acciones”
        colAcciones.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar    = new Button("Editar");
            private final Button btnInscribir = new Button("Inscribir");
            private final Button btnEliminar  = new Button("Eliminar");
            private final HBox pane = new HBox(5, btnEditar, btnInscribir, btnEliminar);
            {
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().forEach(b -> b.getStyleClass().add("action-btn"));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        // Carga los datos
        loadAlumnos();

    }
    private void loadAlumnos() {
        try {
          List<Alumno> alumnos= alumnoDao.findAll();
          tableAlumnos.setItems(FXCollections.observableList(alumnos));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
