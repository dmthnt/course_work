import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import animations.Shake;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RealEstateSceneController {

    @FXML
    private Button AddButton;

    @FXML
    private TextField AddressField;

    @FXML
    private TextField AreaField;

    @FXML
    private Button BackButton;

    @FXML
    private Button ChangeButton;

    @FXML
    private TextField ConstructionDateField;

    @FXML
    private TextField CostField;

    @FXML
    private TableColumn<RealEstate, Long> clientIdColumn;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField FloorField;

    @FXML
    private TableView<RealEstate> RealEstateTable;

    @FXML
    private TextField SearchField;

    @FXML
    private TableColumn<RealEstate, String> addressColumn;

    @FXML
    private TableColumn<RealEstate, Double> areaColumn;

    @FXML
    private Label averageCostText;

    @FXML
    private TableColumn<RealEstate, Year> constructionDateColumn;

    @FXML
    private TableColumn<RealEstate, Double> costColumn;

    @FXML
    private TableColumn<RealEstate, Long> realEstateIdColumn;

    @FXML
    private TableColumn<RealEstate, Integer> floorColumn;

    @FXML
    private TableColumn<RealEstate, Long> idColumn;

    @FXML
    private TableColumn<RealEstate, Integer> roomCountColumn;

    @FXML
    private TextField roomCountField;

    @FXML
    private Button FamoustAreaButton;

    @FXML
    private Button MaxCountRoomButton;

    private long clickedRealEstateId = 0;

    @FXML
    void initialize() {
        showRealEstates();

        FamoustAreaButton.setOnAction(event -> {
            if (RealEstate.realEstates.isEmpty()) {
                showMessage("Нема нерухомості");
                return;
            }

            Double maxAreaCost = 0.0;
            Set<RealEstate> areaList = new HashSet<>();

            for (RealEstate realEstates : RealEstate.realEstates) {
                if (maxAreaCost <= realEstates.getCost()) {
                    if (maxAreaCost < realEstates.getCost()) {
                        areaList.clear();
                        maxAreaCost = realEstates.getCost();
                    }
                    areaList.add(realEstates);
                }
            }

            StringBuilder message = new StringBuilder("Площа що має найбільший попит:\n");

            for (RealEstate realEstates : areaList) {
                message.append("Площа: ").append(realEstates.getArea()).append("\n");
                message.append("Ціна: ").append(realEstates.getCost()).append("\n");
                message.append("\n");
            }

            showMessage(message.toString());
        });

        MaxCountRoomButton.setOnAction(event -> {
            if (RealEstate.realEstates.isEmpty()) {
                showMessage("Нема нерухомості");
                return;
            }

            int maxRoom = 0;
            Set<RealEstate> roomList = new HashSet<>();

            for (RealEstate realEstates : RealEstate.realEstates) {
                if (maxRoom <= realEstates.getRoomCount()) {
                    if (maxRoom < realEstates.getRoomCount()) {
                        roomList.clear();
                        maxRoom = realEstates.getRoomCount();
                    }
                    roomList.add(realEstates);
                }
            }

            StringBuilder message = new StringBuilder("Нерухомість з найбільшою кількістю кімнат:\n");

            for (RealEstate realEstates : roomList) {
                message.append("Адреса: ").append(realEstates.getAddress()).append("\n");
                message.append("Кількість кімнат: ").append(realEstates.getRoomCount()).append("\n");
                message.append("\n");
            }

            showMessage(message.toString());
        });

        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/HomeScene.fxml");

        });

        AddButton.setOnAction(event -> {
            createRealEstate(event, AddressField.getText().trim(), AreaField.getText().trim(),
                    FloorField.getText().trim(),
                    ConstructionDateField.getText().trim(), roomCountField.getText().trim(), CostField.getText());
        });

        ChangeButton.setOnAction(event -> {
            changeRealEstate(event, AddressField.getText().trim(), AreaField.getText().trim(),
                    FloorField.getText().trim(),
                    ConstructionDateField.getText().trim(), roomCountField.getText().trim(), CostField.getText());
        });

        DeleteButton.setOnAction(event -> {
            deleteRealEstate(event);
        });

        SearchField.textProperty().addListener(
                (observable, oldValue, newValue) -> RealEstateTable
                        .setItems(filterList(RealEstate.realEstates, newValue)));

        AreaField.textProperty().addListener(
                (observable, oldValue, newValue) -> averageCostText.setText(String.valueOf(setAverageCost())));
        FloorField.textProperty().addListener(
                (observable, oldValue, newValue) -> averageCostText.setText(String.valueOf(setAverageCost())));
        ConstructionDateField.textProperty().addListener(
                (observable, oldValue, newValue) -> averageCostText.setText(String.valueOf(setAverageCost())));
        roomCountField.textProperty().addListener(
                (observable, oldValue, newValue) -> averageCostText.setText(String.valueOf(setAverageCost())));
    }

    /////////////////////////////////////////////////////

    private void createRealEstate(ActionEvent event, String address, String area, String floor, String constructionDate,
            String roomCount, String cost) {
        if (!address.equals("") && !address.equals("") && !floor.equals("") && !constructionDate.equals("")
                && !roomCount.equals("") && !cost.equals("")) {
            boolean realEstateExists = RealEstate.getRealEstates().stream()
                    .anyMatch(realEstate -> realEstate.getAddress().equals(AddressField.getText()));
            if (check(area, floor, constructionDate, roomCount, cost)) {
                if (!realEstateExists) {
                    RealEstate realEstate = new RealEstate(address, Double.parseDouble(area), Integer.parseInt(floor),
                            Year.parse(constructionDate),
                            Integer.parseInt(roomCount), Double.parseDouble(cost));
                    RealEstate.addRealEstate(realEstate);
                    TextFile.writeRealEstateToFile();
                    showRealEstates();
                    resetText();
                } else {
                    final JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel, "Така адреса вже існує, оберіть іншу", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeRealEstate(ActionEvent event, String address, String area, String floor, String constructionDate,
            String roomCount, String cost) {
        if (!address.equals("") && !address.equals("") && !floor.equals("") && !constructionDate.equals("")
                && !roomCount.equals("") && !cost.equals("")) {
            if (check(area, floor, constructionDate, roomCount, cost)) {
                boolean realEstateExists = RealEstate.getRealEstates().stream()
                        .filter(realEstate -> realEstate.getId() != clickedRealEstateId)
                        .anyMatch(realEstate -> realEstate.getAddress().equals(AddressField.getText()));
                if (!realEstateExists) {
                    for (RealEstate realEstate : RealEstate.realEstates) {

                        if (realEstate.getId() == clickedRealEstateId) {
                            realEstate.setAddress(AddressField.getText().trim());
                            realEstate.setArea(Double.parseDouble(AreaField.getText().trim()));
                            realEstate.setFloor(Integer.parseInt(FloorField.getText().trim()));
                            realEstate.setConstructionDate(Year.parse(ConstructionDateField.getText().trim()));
                            realEstate.setRoomCount(Integer.parseInt(roomCountField.getText().trim()));
                            realEstate.setCost(Double.parseDouble(CostField.getText().trim()));
                            break;
                        }
                    }
                } else {
                    final JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel, "Така адреса вже існує, оберіть іншу", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                TextFile.writeRealEstateToFile();
                resetText();
                RealEstateTable.refresh();
            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteRealEstate(ActionEvent event) {
        int clickedRealEstateIndex = RealEstateTable.getSelectionModel().getSelectedIndex();

        for (Client client : Client.clients) {
            if (client.getIdRealEstate() == RealEstate.realEstates.get(clickedRealEstateIndex).getId()) {
                client.setIdRealEstate(0);
            }
        }
        RealEstateTable.getItems().remove(clickedRealEstateIndex);
        RealEstate.realEstates.remove(clickedRealEstateIndex);
        TextFile.writeRealEstateToFile();
        RealEstateTable.refresh();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Запит");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean searchFindsOrder(RealEstate realEstate, String searchText) {
        return (realEstate.getAddress().toLowerCase().contains(searchText.toLowerCase()));
    }

    private ObservableList<RealEstate> filterList(List<RealEstate> list, String searchText) {
        List<RealEstate> filteredList = new ArrayList<>();
        for (RealEstate realEstate : list) {
            if (searchFindsOrder(realEstate, searchText))
                filteredList.add(realEstate);
        }
        return FXCollections.observableList(filteredList);
    }

    public void rowClicked(MouseEvent event) {
        RealEstate clickedRealEstate = RealEstateTable.getSelectionModel().getSelectedItem();
        clickedRealEstateId = clickedRealEstate.getId();
        AddressField.setText(String.valueOf(clickedRealEstate.getAddress()));
        AreaField.setText(String.valueOf(clickedRealEstate.getArea()));
        FloorField.setText(String.valueOf(clickedRealEstate.getFloor()));
        ConstructionDateField.setText(String.valueOf(clickedRealEstate.getConstructionDate()));
        roomCountField.setText(String.valueOf(clickedRealEstate.getRoomCount()));
        CostField.setText(String.valueOf(clickedRealEstate.getCost()));
    }

    public void showRealEstates() {
        idColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Long>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, String>("address"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Double>("area"));
        floorColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Integer>("floor"));
        constructionDateColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Year>("constructionDate"));
        roomCountColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Integer>("roomCount"));
        costColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Double>("cost"));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<RealEstate, Long>("clientId"));

        RealEstateTable.setItems(RealEstate.getObsRealEstates());
    }

    public boolean check(String area, String floor, String constructionDate,
            String roomCount, String cost) {
        boolean a = true;
        try {
            Double.parseDouble(area);
        } catch (DateTimeParseException e) {
            Shake areaFieldAnim = new Shake(AreaField);
            areaFieldAnim.playAnim();
            a = false;
            e.printStackTrace();
        }
        try {
            Integer.parseInt(floor);
        } catch (DateTimeParseException e) {
            Shake floorFieldAnim = new Shake(FloorField);
            floorFieldAnim.playAnim();
            a = false;
            e.printStackTrace();
        }

        try {
            Year.parse(constructionDate);
        } catch (DateTimeParseException e) {
            Shake constructionDateFieldAnim = new Shake(ConstructionDateField);
            constructionDateFieldAnim.playAnim();
            a = false;
            e.printStackTrace();
        }

        try {
            Double.parseDouble(roomCount);
        } catch (DateTimeParseException e) {
            Shake roomCountFieldAnim = new Shake(roomCountField);
            roomCountFieldAnim.playAnim();
            a = false;
            e.printStackTrace();
        }

        try {
            Double.parseDouble(cost);
        } catch (DateTimeParseException e) {
            Shake costFieldAnim = new Shake(CostField);
            costFieldAnim.playAnim();
            a = false;
            e.printStackTrace();
        }

        return a;

    }

    public int setAverageCost() {
        double totalCount = 0;
        double totalPrice = 0;
        if (!AreaField.getText().equals("") && !FloorField.getText().equals("")
                && !ConstructionDateField.getText().equals("") && !roomCountField.getText().equals("")) {
            try {
                for (RealEstate realEstate : RealEstate.realEstates) {
                    if (realEstate.getArea() == Double.parseDouble(AreaField.getText().trim())
                            && realEstate.getFloor() == Integer.parseInt(FloorField.getText().trim())
                            && realEstate.getConstructionDate()
                                    .equals(Year.parse(ConstructionDateField.getText().trim()))
                            && realEstate.getRoomCount() == Integer.parseInt(roomCountField.getText().trim())) {
                        totalCount++;
                        totalPrice = totalPrice + realEstate.getCost();
                    }
                }
                totalPrice = totalPrice / totalCount;
                return (int) totalPrice;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    public void resetText() {
        AddressField.setText("");
        AreaField.setText("");
        FloorField.setText("");
        ConstructionDateField.setText("");
        roomCountField.setText("");
        CostField.setText("");
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
