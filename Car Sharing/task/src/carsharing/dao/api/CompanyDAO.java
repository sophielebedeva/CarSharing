package carsharing.dao.api;

import carsharing.entity.Company;

import java.util.List;

public interface CompanyDAO {

    void addCompany(String name);

    Integer getIdByName(String name);

    List<Company> getAllCompanies();
}
