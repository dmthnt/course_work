import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client extends People {
    private static AtomicLong ID_GENERATOR = new AtomicLong(1);
    private long id;
    private String serviceType;
    private long idRealEstate;

    public static List<Client> clients = new ArrayList<>();
    public static ObservableList<Client> observableClientList;

    // Client(String firmName, String surname, String name, String patronymic,
    // String phone, String serviceType) {
    // super(firmName, surname, name, patronymic, phone);
    // this.serviceType = serviceType;
    // this.idRearEstate = 0;
    // this.id = ID_GENERATOR.getAndIncrement();

    // }

    Client(String firmName, String surname, String name, String patronymic, String phone, String serviceType,
            long idRealEstate) {
        super(firmName, surname, name, patronymic, phone);
        this.idRealEstate = idRealEstate;
        this.serviceType = serviceType;
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public static void setID_GENERATOR(long i) {
        ID_GENERATOR.set(i + 1);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setIdRealEstate(long idRealEstate) {
        this.idRealEstate = idRealEstate;
    }

    public long getIdRealEstate() {
        return idRealEstate;
    }

    ////////////////////////////////////////

    public static void addClient(Client client) {
        clients.add(client);
        observableClientList = FXCollections.observableArrayList(clients);
    }

    public static void deleteClient(long id) {
        clients.removeIf(client -> client.getId() == id);
        observableClientList = FXCollections.observableArrayList(clients);
    }

    public static void updateClient(long id, Client updatedClient) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == id) {
                clients.set(i, updatedClient);
                break;
            }
        }
        observableClientList = FXCollections.observableArrayList(clients);
    }

    public static List<Client> getClients() {
        return clients;
    }

    public static ObservableList<Client> getObsClients() {
        observableClientList = FXCollections.observableArrayList(clients);
        return observableClientList;
    }

    public static void displayAllClients() {
        for (Client client : clients) {
            System.out.println(client.toString());
        }
    }

    public String toStringFile() {
        return getId() + ", " + getFirmName() + ", " + getSurname() + ", " + getName() + ", " + getPatronymic() + ", "
                + getPhone() + ", "
                + getServiceType() + ", "
                + getIdRealEstate();
    }
}
