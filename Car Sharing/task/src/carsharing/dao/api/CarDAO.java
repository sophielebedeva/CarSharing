package carsharing.dao.api;

import carsharing.entity.Car;

import java.util.List;

public interface CarDAO {

        void addRentedCar(int customerId, int carId);

        List<Car> getAvailableCars(int companyId);

        void returnRentedCar(int customerId);

         void insertCar(String carName, int companyId);
}
