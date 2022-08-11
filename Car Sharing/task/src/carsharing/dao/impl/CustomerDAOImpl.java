package carsharing.dao.impl;

import carsharing.dao.api.CustomerDAO;
import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private final String nameDb;

    public CustomerDAOImpl(String nameDb) {
        this.nameDb = nameDb;
    }

    public List<Customer> getAllCustomers() {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT * FROM CUSTOMER");
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString("name")));
            }
            return customers.isEmpty() ? null : customers;
        } catch (Exception e) {
            System.out.println("Error during getting customers");
            return null;
        }
    }

    @Override
    public Car getRentedCar(String customerName) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT car.name, car.company_id FROM CAR AS car" +
                    " INNER JOIN CUSTOMER as cust ON cust.rented_car_id = car.id\n" +
                    " WHERE cust.name = '"+customerName+"'");
           if (resultSet.next()) {
               return new Car(resultSet.getString("car.name"), resultSet.getInt("car.company_id"));
           }
           else return null;
        } catch (Exception e) {
            return null;
        }
    }


    public void addCustomer(String name) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            st.executeUpdate("INSERT INTO CUSTOMER (name) " +
                    "VALUES " + "('" + name + "')");
        } catch (Exception e) {
            System.out.format("Error during creating connection %s", e.getMessage());
        }
    }
}
