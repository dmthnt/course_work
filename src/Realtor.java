import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Realtor extends People {
    private String address;
    private static AtomicLong ID_GENERATOR = new AtomicLong(1);
    private long id;

    public static List<Realtor> realtors = new ArrayList<>();
    public static ObservableList<Realtor> observableRealtorList;

    Realtor(String firmName, String surname, String name, String patronymic, String phone, String adress) {
        super(firmName, surname, name, patronymic, phone);
        this.address = adress;
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public static void setID_GENERATOR(long i) {
        ID_GENERATOR.set(i + 1);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    ////////////////////////

    public static void addRealtor(Realtor realtor) {
        realtors.add(realtor);
    }

    public static void deleteRealtor(long id) {
        realtors.removeIf(realtor -> realtor.getId() == id);
    }

    public static void updateRealtor(long id, Realtor updatedRealtor) {
        for (int i = 0; i < realtors.size(); i++) {
            if (realtors.get(i).getId() == id) {
                realtors.set(i, updatedRealtor);
                break;
            }
        }
    }

    public static List<Realtor> getRealtors() {
        return realtors;
    }

    public static ObservableList<Realtor> getObsRealtors() {
        observableRealtorList = FXCollections.observableArrayList(realtors);
        return observableRealtorList;
    }

    public static void displayAllRealtors() {
        for (Realtor realtor : realtors) {
            System.out.println(realtor.toString());
        }
    }

    public String toStringFile() {
        return getId() + ", " + getFirmName() + ", " + getSurname() + ", " + getName() + ", " + getPatronymic() + ", "
                + getPhone() + ", "
                + getAddress();
    }
}
