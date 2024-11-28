import java.io.IOException;
import java.time.Year;

import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AvailableRealEstateSceneController {

    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private TableView<RealEstate> RealEstateTable;

    @FXML
    private TableColumn<RealEstate, String> addressColumn;

    @FXML
    private TableColumn<RealEstate, Double> areaColumn;

    @FXML
    private TableColumn<RealEstate, Year> constructionDateColumn;

    @FXML
    private TableColumn<RealEstate, Double> costColumn;

    @FXML
    private TableColumn<RealEstate, Integer> floorColumn;

    @FXML
    private TableColumn<RealEstate, Long> idColumn;

    @FXML
    private TableColumn<RealEstate, Integer> roomCountColumn;

    public static SimpleLongProperty clickedRealEstate = new SimpleLongProperty();

    @FXML
    void initialize() {
        clickedRealEstate.set(0);
        showRealEstates();
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();

        });

        AddButton.setOnAction(event -> {
            clickedRealEstate.set(RealEstateTable.getSelectionModel().getSelectedItem().getId());
            Stage stage = (Stage) AddButton.getScene().getWindow();
            stage.close();
        });
    }

    public void showRealEstates() {
        idColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Long>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, String>("address"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Double>("area"));
        floorColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Integer>("floor"));
        constructionDateColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Year>("constructionDate"));
        roomCountColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Integer>("roomCount"));
        costColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Double>("cost"));

        RealEstateTable.setItems(RealEstate.getObsvAilableRealEstates());
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
