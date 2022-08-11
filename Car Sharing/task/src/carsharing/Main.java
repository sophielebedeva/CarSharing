package carsharing;

import carsharing.commandLineInterface.CompanyMenu;
import carsharing.commandLineInterface.CustomerMenu;
import carsharing.commandLineInterface.Menu;
import carsharing.dao.H2Connector;
import carsharing.dao.api.CarDAO;
import carsharing.dao.api.CompanyDAO;
import carsharing.dao.api.CustomerDAO;
import carsharing.dao.impl.CarDAOImpl;
import carsharing.dao.impl.CompanyDAOImpl;
import carsharing.dao.impl.CustomerDAOImpl;
import carsharing.services.api.CarService;
import carsharing.services.api.CompanyService;
import carsharing.services.api.TableService;
import carsharing.services.impl.CarServiceImpl;
import carsharing.services.impl.CompanyServiceImpl;
import carsharing.services.impl.TableServiceImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String nameDb = "Carshare";
        if (args.length != 0) {
            nameDb = args[1];
        }
        H2Connector h2Connector = new H2Connector(nameDb);
        CompanyDAO companyDAO = new CompanyDAOImpl(nameDb);
        CarDAO carDAO = new CarDAOImpl(nameDb);
        CustomerDAO customerDAO = new CustomerDAOImpl(nameDb);
        TableService tableService = new TableServiceImpl(h2Connector);
        CompanyService companyService = new CompanyServiceImpl(h2Connector, companyDAO);
        CarService carService = new CarServiceImpl(h2Connector, companyService, carDAO);
        CompanyMenu companyMenu = new CompanyMenu(carService, companyService, carDAO);
        CustomerMenu customerMenu = new CustomerMenu(companyService,carService, carDAO, h2Connector, customerDAO);
        Menu menu = new Menu(companyService, companyMenu, customerMenu, customerDAO);
        tableService.execute(
                "CREATE TABLE IF NOT EXISTS COMPANY " +
                        "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR NOT NULL UNIQUE)");
        tableService.execute(
                "CREATE TABLE IF NOT EXISTS CAR " +
                "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR NOT NULL UNIQUE, " +
                "company_id INTEGER NOT NULL, " +
                "CONSTRAINT fk_id FOREIGN KEY (company_id) " +
                "REFERENCES COMPANY(id))");
        tableService.execute(
                "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                        "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR NOT NULL UNIQUE, " +
                        "rented_car_id INTEGER DEFAULT NULL, " +
                        "CONSTRAINT fk_carId FOREIGN KEY (rented_car_id) " +
                        "REFERENCES CAR(id))"
        );
        menu.showShortMenu();
    }
}