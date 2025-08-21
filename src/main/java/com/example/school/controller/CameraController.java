package com.example.school.controller;

import com.example.school.dao.impl.AlumnoDao;
import com.example.school.model.Alumno;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CameraController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnCapture;

    private VideoCapture capture;
    private boolean cameraActive = false;
    private static final String IMG_DIR = "src/main/resources/img";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OpenCV.loadShared();
        capture = new VideoCapture(0);
        if (capture.isOpened()) {
            cameraActive = true;
            startCameraStream();
        } else {
            System.err.println("No se pudo acceder a la cámara.");
        }
    }

    private void startCameraStream() {
        Thread thread = new Thread(() -> {
            while (cameraActive) {
                Mat frame = new Mat();
                if (capture.read(frame)) {
                    Image imageToShow = mat2Image(frame);
                    Platform.runLater(() -> imageView.setImage(imageToShow));
                }
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    @FXML
    public void onCapture() {
        Mat frame = new Mat();
        if (capture.isOpened() && capture.read(frame)) {
            File dir = new File(IMG_DIR);
            if (!dir.exists()) dir.mkdirs();

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = IMG_DIR + File.separator + "alumno_" + timestamp + ".png";

            if (Imgcodecs.imwrite(filename, frame)) {
                System.out.println("Imagen guardada en: " + filename);
                updateAlumnoPath(filename);
            } else {
                System.err.println("Error al guardar la imagen.");
            }
        }
    }

    private void updateAlumnoPath(String path) {
        try {
            AlumnoDao dao = new AlumnoDao();
            Alumno alumno = dao.findById(1); // <- cambia esto por el ID real
            alumno.setPath(path);
            dao.updatePhotoPath(alumno.getId(), path);
            System.out.println("Ruta de imagen actualizada en BD.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCamera() {
        cameraActive = false;
        if (capture != null) {
            capture.release();
        }
    }
}
