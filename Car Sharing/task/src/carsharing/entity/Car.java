package carsharing.entity;

public class Car {
    private String name;
    private int companyId;

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }
}
