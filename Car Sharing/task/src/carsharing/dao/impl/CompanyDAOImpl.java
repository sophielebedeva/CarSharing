package carsharing.dao.impl;

import carsharing.dao.api.CompanyDAO;
import carsharing.entity.Company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    private final String nameDb;

    public CompanyDAOImpl(String nameDb) {
        this.nameDb = nameDb;
    }

    @Override
    public void addCompany(String name) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            st.executeUpdate("INSERT INTO COMPANY (name) " +
                    "VALUES " + "('" + name + "')");
        } catch (Exception e) {
            System.out.format("Error during creating connection %s", e.getMessage());
        }
    }

    @Override
    public Integer getIdByName(String name) {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();
        ) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT id FROM COMPANY"  +
                    " WHERE name = " + "'" + name + "'");
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            System.out.println("Error during getting id");
            return null;
        }
    }


    @Override
    public List<Company> getAllCompanies() {
        try (Connection conn = DriverManager
                .getConnection("jdbc:h2:/Users/user/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + nameDb);
             Statement st = conn.createStatement();) {
            conn.setAutoCommit(true);
            ResultSet resultSet = st.executeQuery("SELECT * FROM COMPANY");
            List<Company> companies = new ArrayList<>();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
            }
            return companies.isEmpty() ? null : companies;
        } catch (Exception e) {
            System.out.println("Error during getting companies");
            return null;
        }
    }
}
