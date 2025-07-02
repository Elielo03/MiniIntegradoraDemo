package com.example.school.controller;


import com.example.school.dao.impl.UsuarioDao;
import com.example.school.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private TextField txtPass;

    UsuarioDao usuarioDao = new UsuarioDao();
    @FXML
    private void onLogin(ActionEvent event){
        String correo = txtCorreo.getText().trim();
        String pass = txtPass.getText().trim();
        Usuario usuario = new Usuario();
        try {
            if(usuarioDao.login(correo, pass)){
                System.out.println("Usuario encontrado");
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/example/school/views/Dashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            }else{
                showAlert("Credenciales Invalidas" , "Revisa tu correo o pass");
            }

        } catch (Exception e) {
            e.printStackTrace();
           showAlert("Error al inciar sesión", e.getMessage());
        }

    }

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
