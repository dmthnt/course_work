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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RealtorSceneController {

    @FXML
    private Button AddButton;

    @FXML
    private TextField AddressField;

    @FXML
    private TableColumn<Realtor, String> addressColumn;

    @FXML
    private Button BackButton;

    @FXML
    private Button ChangeButton;

    @FXML
    private TableView<Realtor> RealtorTable;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PatronymicField;

    @FXML
    private TextField PhoneField;

    @FXML
    private TextField SurnameField;

    @FXML
    private TableColumn<Realtor, String> firmNameColumn;

    @FXML
    private TextField FirmNameField;

    @FXML
    private TableColumn<Realtor, Long> idColumn;

    @FXML
    private TableColumn<Realtor, String> nameColumn;

    @FXML
    private TableColumn<Realtor, String> patronymicColumn;

    @FXML
    private TableColumn<Realtor, String> phoneColumn;

    @FXML
    private TableColumn<Realtor, String> surnameColumn;

    private long clickedRealtorId = 0;

    @FXML
    void initialize() {
        showRealtors();
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            openNewScene("/assets/scenes/HomeScene.fxml");

        });

        AddButton.setOnAction(event -> {
            createRealtor(event, FirmNameField.getText().trim(), NameField.getText().trim(),
                    SurnameField.getText().trim(),
                    PatronymicField.getText().trim(), PhoneField.getText().trim(), AddressField.getText());
        });

        ChangeButton.setOnAction(event -> {
            changeRealtor(event, FirmNameField.getText().trim(), NameField.getText().trim(),
                    SurnameField.getText().trim(),
                    PatronymicField.getText().trim(), PhoneField.getText().trim(), AddressField.getText());
        });

        DeleteButton.setOnAction(event -> {
            deleteRealtor(event);
        });

    }

    /////////////////////////////////////////////////////

    private void createRealtor(ActionEvent event, String firmName, String surname, String name, String patronymic,
            String phone, String address) {
        if (!firmName.equals("") && !name.equals("") && !surname.equals("") && !patronymic.equals("")
                && !address.equals("")) {

            if (check(surname, name, patronymic, phone)) {
                resetText();
                Realtor realtor = new Realtor(firmName, surname, name, patronymic, phone, address);
                Realtor.addRealtor(realtor);
                TextFile.writeRealtorToFile();
                showRealtors();
            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeRealtor(ActionEvent event, String firmName, String surname, String name, String patronymic,
            String phone, String address) {
        if (!name.equals("") && !surname.equals("") && !patronymic.equals("")
                && !phone.equals("")) {
            if (check(surname, name, patronymic, phone)) {
                for (Realtor realtor : Realtor.getRealtors()) {
                    if (realtor.getId() == clickedRealtorId) {
                        realtor.setFirmName(FirmNameField.getText().trim());
                        realtor.setName(NameField.getText().trim());
                        realtor.setSurname(SurnameField.getText().trim());
                        realtor.setPatronymic(PatronymicField.getText().trim());
                        realtor.setPhone(PhoneField.getText().trim());
                        realtor.setAddress(AddressField.getText().trim());
                        break;
                    }
                }
                TextFile.writeRealtorToFile();
                resetText();
                RealtorTable.refresh();
            }
        } else {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Заповніть усі поля", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteRealtor(ActionEvent event) {
        int clickedRealtorIndex = RealtorTable.getSelectionModel().getSelectedIndex();

        RealtorTable.getItems().remove(clickedRealtorIndex);
        Realtor.realtors.remove(clickedRealtorIndex);
        TextFile.writeRealtorToFile();
        RealtorTable.refresh();
    }

    public void rowClicked(MouseEvent event) {
        Realtor clickedRealtor = RealtorTable.getSelectionModel().getSelectedItem();
        clickedRealtorId = clickedRealtor.getId();
        FirmNameField.setText(clickedRealtor.getFirmName());
        NameField.setText(clickedRealtor.getName());
        SurnameField.setText(clickedRealtor.getSurname());
        PatronymicField.setText(clickedRealtor.getPatronymic());
        PhoneField.setText(clickedRealtor.getPhone());
        AddressField.setText(clickedRealtor.getAddress());
    }

    public void showRealtors() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Realtor, Long>("id"));
        firmNameColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("firmName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("patronymic"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Realtor, String>("address"));

        RealtorTable.setItems(Realtor.getObsRealtors());
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
        AddressField.setText("");
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
