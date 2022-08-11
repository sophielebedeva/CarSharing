package carsharing.dao;

import carsharing.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2Connector {
    private final String nameDb;

    public H2Connector(String nameDb) {
        this.nameDb = nameDb;
    }

    public void createTable(String sql) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement()) {
            conn.setAutoCommit(true);
            st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Error during creating database");
        }
    }


    public List<Customer> selectCustomers(String tableName) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT * FROM " + tableName);
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString("name")));
            }
            return customers;
        } catch (Exception e) {
            System.out.println("Error during getting customers");
            return new ArrayList<>();
        }
    }

    public List<String> selectCars(String companyName) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            int id = getIdByName("COMPANY", companyName);
            ResultSet resultSet = st.executeQuery("SELECT * FROM CAR " +
                    "WHERE company_id = " + id);
            List<String> companyCars = new ArrayList<>();
            int orderNumber = 1;
            while ((resultSet.next())) {
                companyCars.add(orderNumber++ + ". " + resultSet.getString("name"));
            }
            return companyCars.isEmpty() ? null : companyCars;
        } catch (Exception e) {
            System.out.println("Error during getting cars");
            return null;
        }
    }

    public Integer getIdByName(String tableName, String entityName) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT id FROM " + tableName +
                    " WHERE name = '" + entityName + "'");
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            System.out.println("Error during getting id by name");
            return null;
        }
    }

    public String getNameById(String tableName, int id) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT name FROM " + tableName +
                    " WHERE id = " + id);
            resultSet.next();
            return resultSet.getString("name");
        } catch (Exception e) {
            System.out.println("Error during getting name");
            return null;
        }
    }
}
