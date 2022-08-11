package carsharing.dao.api;

import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    List<Customer> getAllCustomers();

    Car getRentedCar(String customerName);

    void addCustomer(String name);
}
