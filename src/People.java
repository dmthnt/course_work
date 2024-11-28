public abstract class People {
    private String firmName;
    private String name;
    private String patronymic;
    private String surname;

    private String phone;

    People() {
    };

    People(String firmName, String surname, String name, String patronymic, String phone) {
        this.firmName = firmName;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone = phone;
    }

    public String getFirmName() {
        return firmName;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname=" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic=" + patronymic + '\'' +
                ", phone='" + phone + "}";
    }

}
