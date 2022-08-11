package carsharing.services.api;

import carsharing.entity.Car;

import java.util.List;

public interface CarService {

    List<String> getAllCars(String companyName);
    List<Car> getAvailableCars(int companyId);
}
