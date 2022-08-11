package carsharing.services.impl;

import carsharing.dao.H2Connector;
import carsharing.dao.api.CompanyDAO;
import carsharing.dao.impl.CompanyDAOImpl;
import carsharing.entity.Company;
import carsharing.services.api.CompanyService;

import java.sql.SQLException;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    private final H2Connector h2Connector;
    private final CompanyDAO companyDAO;

    public CompanyServiceImpl(H2Connector h2Connector, CompanyDAO companyDAO) {
        this.h2Connector = h2Connector;
        this.companyDAO = companyDAO;
    }

    @Override
    public void create(String tableName, String companyName) {
        companyDAO.addCompany(companyName);
    }

    @Override
    public int getIdByName(String companyName) {
        return companyDAO.getIdByName(companyName);
    }

    @Override
    public String getNameById(int id) {
        return h2Connector.getNameById("COMPANY",id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDAO.getAllCompanies();
    }
}
