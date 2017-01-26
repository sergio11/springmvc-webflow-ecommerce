package web.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.models.Country;
import persistence.repositories.CountryRepository;
import web.services.CountryService;

/**
 * @author sergio
 */
@Service("countryService")
public class CountryServiceImpl implements CountryService {
    
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}
