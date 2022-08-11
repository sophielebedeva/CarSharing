package carsharing.services.impl;

import carsharing.dao.H2Connector;
import carsharing.dao.api.CarDAO;
import carsharing.entity.Car;
import carsharing.services.api.CarService;
import carsharing.services.api.CompanyService;
import java.util.List;

public class CarServiceImpl implements CarService {
    private final H2Connector h2Connector;
    private final CompanyService companyService;
    private final CarDAO carDAO;


    public CarServiceImpl(H2Connector h2Connector, CompanyService companyService, CarDAO carDAO) {
        this.h2Connector = h2Connector;
        this.companyService = companyService;
        this.carDAO = carDAO;
    }

    @Override
    public List<Car> getAvailableCars(int companyId) {
        return carDAO.getAvailableCars(companyId);
    }

    @Override
    public List<String> getAllCars(String companyName) {
        return h2Connector.selectCars(companyName);
    }
}
