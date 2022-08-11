package carsharing.dao.impl;

import carsharing.dao.api.CarDAO;
import carsharing.entity.Car;
import carsharing.entity.Company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    String nameDb;

    public CarDAOImpl(String nameDb) {
        this.nameDb = nameDb;
    }

    public void insertCar(String carName, int companyId) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            st.executeUpdate("INSERT INTO CAR (name, company_id) " +
                    "VALUES " + "('" + carName + "', " + companyId + ")");
        } catch (Exception e) {
            System.out.format("Error during creating connection %s", e.getMessage());
        }
    }

    @Override
    public void addRentedCar(int customerId, int carId) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            st.executeUpdate("UPDATE CUSTOMER" +
                    " SET rented_car_id = " + carId +
                    " WHERE id = " + customerId);
        } catch (Exception e) {
            System.out.format("Error during adding your rented car, %s", e.getMessage());
        }
    }

    @Override
    public List<Car> getAvailableCars(int companyId) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT car.name, car.company_id FROM CAR as car" +
                    " LEFT JOIN CUSTOMER AS cust ON car.id = cust.rented_car_id" +
                    " WHERE cust.rented_car_id IS NULL AND car.company_id = " + companyId);
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getString("name"), resultSet.getInt("company_id")));
            }
            return cars;
        } catch (Exception e) {
            System.out.format("Error during adding your rented car, %s", e.getMessage());
            return null;
        }
    }

    @Override
    public void returnRentedCar(int customerId) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            st.executeUpdate("UPDATE CUSTOMER " +
                    "SET rented_car_id = NULL " +
                    "WHERE id = " + customerId);
        } catch (Exception e) {
            System.out.format("Error during returning your car, %s", e.getMessage());
        }
    }
}
