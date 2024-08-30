package org.acgprojeto.util;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alertas {

    public static void mostrarAlerta(String title,   String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

    public static Alert retornaAlerta(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }
}
