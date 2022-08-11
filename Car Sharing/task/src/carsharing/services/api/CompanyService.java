package carsharing.services.api;
import carsharing.entity.Company;
import java.util.List;

public interface CompanyService {

    void create(String tableName, String companyName);

    List<Company> getAllCompanies();

    int getIdByName(String companyName);

    String getNameById(int id);

}
