package web.services;

import java.util.List;
import persistence.models.Country;

/**
 * @author sergio
 */
public interface CountryService {
    List<Country> getAll();
}
