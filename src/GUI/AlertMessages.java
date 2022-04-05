package GUI;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AlertMessages {
    public static void getAlert(String errMsg) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setAlertType(AlertType.ERROR);
        alert.setContentText(errMsg);
        alert.show();
    }
}
