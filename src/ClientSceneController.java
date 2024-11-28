import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import animations.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientSceneController {

    @FXML
    private Button AddButton;

    @FXML
    private Button AddRealEstateButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button ChangeButton;

    @FXML
    private TableView<Client> ClientTable;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PatronymicField;

    @FXML
    private TextField PhoneField;

    @FXML
    private ComboBox<String> ServiceTypeComboBox;

    @FXML
    private TextField SurnameField;

    @FXML
    private TableColumn<Client, String> firmNameColumn;

    @FXML
    private TextField FirmNameField;

    @FXML
    private TableColumn<Client, Long> idColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> patronymicColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, Long> realEstateColumn;

    @FXML
    public Label realEstateIdText;

    @FXML
    private TableColumn<Client, String> serviceColumn;

    @FXML
    private TableColumn<Client, String> surnameColumn;

    private long clickedClientId = 0;

    @FXML
    void initialize() {
        showClients();
        ServiceTypeComboBox.getItems().addAll("Пошук та Продаж", "Оренда", "Оцінка Нерухомості", "Юридична Підтримка",
                "Консультації та Поради", "Управління Нерухомістю");

        AddRealEstateButton.setDisable(true);
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/HomeScene.fxml");

        });

        AddButton.setOnAction(event -> {
            createClient(event, FirmNameField.getText().trim(), NameField.getText().trim(),
                    SurnameField.getText().trim(),
                    PatronymicField.getText().trim(), PhoneField.getText().trim(), ServiceTypeComboBox.getValue(),
                    realEstateIdText.getText());
        });

        ChangeButton.setOnAction(event -> {
            changeClient(event, FirmNameField.getText().trim(), NameField.getText().trim(),
                    SurnameField.getText().trim(),
                    PatronymicField.getText().trim(), PhoneField.getText().trim(), ServiceTypeComboBox.getValue(),
                    realEstateIdText.getText());
        });

        AddRealEstateButton.setOnAction(event -> {
            RealEstate.availableRealEstates.clear();
            RealEstate.setAvailableRealEstates();
            openNewScene("/assets/scenes/AvailableRealEstateScene.fxml");

        });

        DeleteButton.setOnAction(event -> {
            deleteClient(event);
        });

        ServiceTypeComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> lockComboBox());

        AvailableRealEstateSceneController.clickedRealEstate.addListener(
                (observable, oldValue, newValue) -> realEstateIdText.setText(String.valueOf(newValue)));
    }

    /////////////////////////////////////////////////////

    private void createClient(ActionEvent event, String firmName, String surname, String name, String patronymic,
            String phone, String serviceType,
            String idRealEstate) {
        if (!firmName.equals("") && !name.equals("") && !surname.equals("") && !patronymic.equals("")
                && !phone.equals("") && !(serviceType == null)) {
            boolean clientExists = Client.getClients().stream()
                    .anyMatch(client -> client.getIdRealEstate() == Long.parseLong(realEstateIdText.getText()));
            if (check(surname, name, patronymic, phone)) {
                resetText();
                if (!clientExists) {
                    Client client = new Client(firmName, surname, name, patronymic, phone, serviceType,
                            Long.parseLong(idRealEstate));
                    if (Long.parseLong(idRealEstate) != 0) {
                        for (RealEstate realEstate : RealEstate.realEstates) {
                            if (realEstate.getId() == Long.parseLong(idRealEstate)) {
                                realEstate.setClientId(client.getId());
                                TextFile.writeRealEstateToFile();
                                break;
                            }
                        }
                    }

                    Client.addClient(client);
                    TextFile.writeClientToFile();
                    showClients();
                }

            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeClient(ActionEvent event, String firmName, String surname, String name, String patronymic,
            String phone, String serviceType,
            String idRealEstate) {
        if (!name.equals("") && !surname.equals("") && !patronymic.equals("")
                && !phone.equals("")) {
            if (check(surname, name, patronymic, phone)) {
                boolean clientExists = Client.getClients().stream()
                        .filter(client -> client.getId() != clickedClientId)
                        .anyMatch(client -> client.getIdRealEstate() == Long.parseLong(realEstateIdText.getText()));
                for (Client client : Client.getClients()) {
                    if (!clientExists) {
                        if (client.getId() == clickedClientId) {
                            if (Long.parseLong(idRealEstate) != 0) {
                                for (RealEstate realEstate : RealEstate.realEstates) {
                                    if (realEstate.getId() == client.getIdRealEstate()) {
                                        realEstate.setClientId(0);
                                        break;
                                    }
                                }
                            }
                            client.setFirmName(FirmNameField.getText().trim());
                            client.setName(NameField.getText().trim());
                            client.setSurname(SurnameField.getText().trim());
                            client.setPatronymic(PatronymicField.getText().trim());
                            client.setPhone(PhoneField.getText().trim());
                            client.setServiceType(ServiceTypeComboBox.getValue());
                            client.setIdRealEstate(Long.parseLong(realEstateIdText.getText()));
                            if (Long.parseLong(idRealEstate) != 0) {
                                for (RealEstate realEstate : RealEstate.realEstates) {
                                    if (realEstate.getId() == Long.parseLong(idRealEstate)) {
                                        realEstate.setClientId(client.getId());
                                        TextFile.writeRealEstateToFile();
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    } else {
                        final JPanel panel = new JPanel();
                        JOptionPane.showMessageDialog(panel, "Така нерухомість вже прив'язана до клієнта", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                TextFile.writeClientToFile();
                resetText();
                ClientTable.refresh();
            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteClient(ActionEvent event) {
        int clickedClientIndex = ClientTable.getSelectionModel().getSelectedIndex();

        for (RealEstate realEstate : RealEstate.realEstates) {
            if (realEstate.getId() == Client.clients.get(clickedClientIndex).getIdRealEstate()) {
                realEstate.setClientId(0);
            }
        }
        ClientTable.getItems().remove(clickedClientIndex);
        Client.clients.remove(clickedClientIndex);
        TextFile.writeClientToFile();
        ClientTable.refresh();
    }

    public void rowClicked(MouseEvent event) {
        Client clickedClient = ClientTable.getSelectionModel().getSelectedItem();
        clickedClientId = clickedClient.getId();
        FirmNameField.setText(String.valueOf(clickedClient.getFirmName()));
        NameField.setText(String.valueOf(clickedClient.getName()));
        SurnameField.setText(String.valueOf(clickedClient.getSurname()));
        PatronymicField.setText(String.valueOf(clickedClient.getPatronymic()));
        PhoneField.setText(String.valueOf(clickedClient.getPhone()));
        ServiceTypeComboBox.setValue(String.valueOf(clickedClient.getServiceType()));
        realEstateIdText.setText(String.valueOf(clickedClient.getIdRealEstate()));
    }

    public void lockComboBox() {
        String selectedValue = ServiceTypeComboBox.getValue();

        if (selectedValue != null && (selectedValue.equals("Пошук та Продаж") ||
                selectedValue.equals("Оренда") ||
                selectedValue.equals("Оцінка Нерухомості") ||
                selectedValue.equals("Управління Нерухомістю"))) {
            AddRealEstateButton.setDisable(false);
        } else {
            AddRealEstateButton.setDisable(true);
            realEstateIdText.setText("0");
        }
    }

    public void showClients() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));
        firmNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firmName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("patronymic"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("serviceType"));
        realEstateColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("idRealEstate"));

        ClientTable.setItems(Client.getObsClients());
    }

    public boolean check(String surname, String name, String patronymic, String phone) {
        boolean a = true;
        for (char c : surname.toCharArray()) {
            if (Character.isDigit(c)) {
                Shake surnameFieldAnim = new Shake(SurnameField);
                surnameFieldAnim.playAnim();
                a = false;
                break;
            }
        }
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                Shake nameFieldAnim = new Shake(NameField);
                nameFieldAnim.playAnim();
                a = false;
                break;
            }
        }
        for (char c : patronymic.toCharArray()) {
            if (Character.isDigit(c)) {
                Shake patronymicFieldAnim = new Shake(PatronymicField);
                patronymicFieldAnim.playAnim();
                a = false;
                break;
            }
        }

        if (phone.length() == 10) {
            for (char c : phone.toCharArray()) {
                if (!Character.isDigit(c)) {
                    Shake phoneFieldAnim = new Shake(PhoneField);
                    phoneFieldAnim.playAnim();
                    a = false;
                    break;
                }
            }
        } else {
            Shake phoneFieldAnim = new Shake(PhoneField);
            phoneFieldAnim.playAnim();
            a = false;
        }

        return a;

    }

    public void resetText() {
        FirmNameField.setText("");
        NameField.setText("");
        SurnameField.setText("");
        PatronymicField.setText("");
        PhoneField.setText("");
        ServiceTypeComboBox.setValue(null);
        realEstateIdText.setText("0");
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
