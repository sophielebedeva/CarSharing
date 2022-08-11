package carsharing.commandLineInterface;

import carsharing.dao.H2Connector;
import carsharing.dao.api.CarDAO;
import carsharing.dao.api.CustomerDAO;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;
import carsharing.services.api.CarService;
import carsharing.services.api.CompanyService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class CustomerMenu {
    Scanner scanner = new Scanner(System.in);
    private final CompanyService companyService;
    private final CarService carService;
    private final CarDAO carDAO;
    private final H2Connector h2Connector;
    private final CustomerDAO customerDAO;


    public CustomerMenu(CompanyService companyService, CarService carService, CarDAO carDAO, H2Connector h2Connector, CustomerDAO customerDAO) {
        this.companyService = companyService;
        this.carService = carService;
        this.carDAO = carDAO;
        this.h2Connector = h2Connector;
        this.customerDAO = customerDAO;
    }


    void showCustomerMenu(String customerName) {
        String command = "";
        while (!command.equals("0")) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");
            command = scanner.nextLine();
            switch (command) {
                case "0":
                    break;
                case "1":
                    rentACar(customerName);
                    break;
                case "2":
                    returnACar(customerName);
                    break;
                case "3":
                    getRentedCarInfo(customerName);
            }
        }
    }

    public void rentACar(String customerName) {
        if (Optional.ofNullable(customerDAO.getRentedCar(customerName)).isPresent()) {
            System.out.println("You've already rented a car!");
        } else { //choosing a company
            List<Company> companiesToShow = companyService.getAllCompanies();
            Optional.ofNullable(companiesToShow)
                    .ifPresentOrElse(companies -> {
                        System.out.println("Choose the company:");
                        int i = 1;
                        for (Company company : companiesToShow) {
                            System.out.println(i++ + ". " + company.getName());
                        }
                        System.out.println("0. Back");
                        int companyChosen = scanner.nextInt();
                        scanner.nextLine();
                        if (companyChosen == 0) {
                            return;
                        }
                        // choosing a car
                        List<Car> cars = carService.getAvailableCars(companiesToShow.get(companyChosen - 1).getId());
                        int k = 1;
                        if (cars.isEmpty()) {
                            System.out.println("The car list is empty!");
                        } else {
                            System.out.println("Choose a car:");
                            for (Car car : cars) {
                                System.out.println(k++ + ". " + car.getName());
                            }
                            System.out.println("0. Back");
                            int carToRent = scanner.nextInt();
                            scanner.nextLine();
                            if (carToRent == 0) {
                                return;
                            }
                            String rentedCar = cars.get(carToRent - 1).getName();
                            System.out.printf("You rented '%s'\n", rentedCar);
                            carDAO.addRentedCar(h2Connector.getIdByName("CUSTOMER", customerName), h2Connector.getIdByName("CAR", rentedCar));
                        }

                    }, () -> System.out.println("The company list is empty!"));

        }
    }
    public void returnACar(String customerName){
        Optional.ofNullable(customerDAO.getRentedCar(customerName)).ifPresentOrElse(c -> {
                    carDAO.returnRentedCar(h2Connector.getIdByName("CUSTOMER", customerName));
                    System.out.println("You've returned a rented car!");
                }
                , () -> System.out.println("You didn't rent a car!"));
    }

    public void getRentedCarInfo(String customerName){
        try {
            Car car = customerDAO.getRentedCar(customerName);
            String companyName = companyService.getNameById(car.getCompanyId());
            System.out.println("Your rented car : " + car.getName());
            System.out.println("Company: " + companyName);
        } catch (NullPointerException e) {
            System.out.println("You didn't rent a car!");
        }
    }

    void showCustomerList(List<Customer> customersToShow) {
        String customerChosen = "";
        while (!customerChosen.equals("0")) {
            System.out.println("Customer list:");
            int i = 1;
            for (Customer customer : customersToShow) {
                System.out.println(i++ + ". " + customer.getName());
            }
            System.out.println("0. Back");
            customerChosen = scanner.nextLine();
            if (Objects.equals(customerChosen, "0")) {
                break;
            }
            showCustomerMenu(customersToShow.get(Integer.parseInt(customerChosen) - 1).getName());
        }
    }
}
