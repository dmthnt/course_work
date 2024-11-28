import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeSceneController {

    @FXML
    private Button ClientSceneButton;

    @FXML
    private Button RealEstateSceneButton;

    @FXML
    private Button RealtorSceneButton;

    @FXML
    void initialize() {

        ClientSceneButton.setOnAction(event -> {
            Stage stage = (Stage) ClientSceneButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/ClientScene.fxml");

        });
        RealtorSceneButton.setOnAction(event -> {
            Stage stage = (Stage) RealtorSceneButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/RealtorScene.fxml");

        });
        RealEstateSceneButton.setOnAction(event -> {
            Stage stage = (Stage) RealEstateSceneButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/RealEstateScene.fxml");

        });
    }

    public void openNewScene(String window) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
