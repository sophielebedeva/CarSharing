package carsharing.commandLineInterface;

import carsharing.dao.api.CarDAO;
import carsharing.services.api.CarService;
import carsharing.services.api.CompanyService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CompanyMenu {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;
    private final CompanyService companyService;
    private final CarDAO carDAO;

    public CompanyMenu(CarService carService, CompanyService companyService, CarDAO carDAO) {
        this.carService = carService;
        this.companyService = companyService;
        this.carDAO = carDAO;
    }


    public void showCarMenu(String companyName) {
        String command = "";
        System.out.printf("'%s' company\n", companyName);
        while (!command.equals("0")) {
            System.out.println("1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    showCarList(companyName);
                    break;
                case "2":
                  createCar(companyName);
            }
        }
    }

    public void showCarList(String companyName) {
        List<String> carsToShow = carService.getAllCars(companyName);
        Optional.ofNullable(carsToShow)
                .ifPresentOrElse(cars-> carsToShow.forEach(System.out::println),
                        () -> System.out.println("The car list is empty!"));
    }

    public void createCar(String companyName){
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        carDAO.insertCar(carName, companyService.getIdByName(companyName));
        System.out.println("The car was added!");
    }
}
