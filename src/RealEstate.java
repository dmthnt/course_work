import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RealEstate {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private long id;

    private String address;
    private Double area;
    private int floor;
    private Year constructionDate;
    private int roomCount;
    private Double cost;
    private long clientId;

    public static List<RealEstate> realEstates = new ArrayList<>();
    public static ObservableList<RealEstate> observableRealEstateList;

    public static List<RealEstate> availableRealEstates = new ArrayList<>();
    public static ObservableList<RealEstate> observableAvailableRealEstateList;

    public RealEstate(String address, Double area, int floor, Year constructionDate, int roomCount, Double cost) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.address = address;
        this.area = area;
        this.floor = floor;
        this.constructionDate = constructionDate;
        this.roomCount = roomCount;
        this.cost = cost;
        this.clientId = 0;
    }

    // Геттери
    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Double getArea() {
        return area;
    }

    public int getFloor() {
        return floor;
    }

    public Year getConstructionDate() {
        return constructionDate;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public Double getCost() {
        return cost;
    }

    public long getClientId() {
        return clientId;
    }

    // Сеттери

    public static void setID_GENERATOR(long i) {
        ID_GENERATOR.set(i + 1);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setConstructionDate(Year constructionDate) {
        this.constructionDate = constructionDate;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    ////////////////////////////

    public static void addRealEstate(RealEstate realEstate) {
        realEstates.add(realEstate);
    }

    public static void deleteRealEstate(long id) {
        realEstates.removeIf(realEstate -> realEstate.getId() == id);
    }

    public static void updateRealEstate(long id, RealEstate updatedRealEstate) {
        for (int i = 0; i < realEstates.size(); i++) {
            if (realEstates.get(i).getId() == id) {
                realEstates.set(i, updatedRealEstate);
                break;
            }
        }
    }

    /////////////////

    public static void setAvailableRealEstates() {
        for (RealEstate realEstate : RealEstate.realEstates) {
            if (realEstate.getClientId() == 0) {
                availableRealEstates.add(realEstate);
            }
        }
    }

    public static ObservableList<RealEstate> getObsvAilableRealEstates() {
        observableAvailableRealEstateList = FXCollections.observableArrayList(availableRealEstates);
        return observableAvailableRealEstateList;
    }

    public static ObservableList<RealEstate> getObsRealEstates() {
        observableRealEstateList = FXCollections.observableArrayList(realEstates);
        return observableRealEstateList;
    }

    public static List<RealEstate> getRealEstates() {
        return realEstates;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", floor=" + floor +
                ", constructionDate=" + constructionDate +
                ", roomCount=" + roomCount +
                ", cost=" + cost +
                '}';
    }

    public String toStringFile() {
        return getId() + ", " + getAddress() + ", " + getArea() + ", " +
                getFloor() + ", " + getConstructionDate() + ", " +
                getRoomCount() + ", " + getCost() + ", " + getClientId();
    }
}
