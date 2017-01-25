package persistence.populator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.Country;
import persistence.repositories.CountryRepository;

/**
 * @author sergio
 */
@Component
@Profile("development")
public class CountryPopulator implements Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(CountryPopulator.class);

    @Autowired
    private CountryRepository countryRepository;
    
    @Order(1)
    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void contextRefreshedEvent() {
        logger.info("SETUP COUNTRY INIT DATA");
        List<Country> countries = new ArrayList();
        countries.add(new Country("AF", "Afghanistan"));
        countries.add(new Country("AL", "Albania"));
        countries.add(new Country("DZ", "Algeria"));
        countries.add(new Country("DS", "American Samoa"));
        countries.add(new Country("AD", "Andorra"));
        countries.add(new Country("AO", "Angola"));
        countries.add(new Country("AI", "Anguilla"));
        countries.add(new Country("AQ", "Antarctica"));
        countries.add(new Country("AG", "Antigua and Barbuda"));
        countries.add(new Country("AR", "Argentina"));
        countries.add(new Country("AM", "Armenia"));
        countries.add(new Country("AW", "Aruba"));
        countries.add(new Country("AU", "Australia"));
        countries.add(new Country("AT", "Austria"));
        countries.add(new Country("AZ", "Azerbaijan"));
        countries.add(new Country("BS", "Bahamas"));
        countries.add(new Country("BH", "Bahrain"));
        countries.add(new Country("BD", "Bangladesh"));
        countries.add(new Country("BB", "Barbados"));
        countries.add(new Country("BY", "Belarus"));
        countries.add(new Country("BE", "Belgium"));
        countries.add(new Country("BZ", "Belize"));
        countries.add(new Country("BJ", "Benin"));
        countries.add(new Country("BM", "Bermuda"));
        countries.add(new Country("BT", "Bhutan"));
        countries.add(new Country("BO", "Bolivia"));
        countries.add(new Country("BA", "Bosnia and Herzegovina"));
        countries.add(new Country("BW", "Botswana"));
        countries.add(new Country("BV", "Bouvet Island"));
        countries.add(new Country("BR", "Brazil"));
        countries.add(new Country("IO", "British Indian Ocean Territory"));
        countries.add(new Country("BN", "Brunei Darussalam"));
        countries.add(new Country("BG", "Bulgaria"));
        countries.add(new Country("BF", "Burkina Faso"));
        countries.add(new Country("BI", "Burundi"));
        countries.add(new Country("KH", "Cambodia"));
        countries.add(new Country("CM", "Cameroon"));
        countries.add(new Country("CA", "Canada"));
        countries.add(new Country("CV", "Cape Verde"));
        countries.add(new Country("KY", "Cayman Islands"));
        countries.add(new Country("CF", "Central African Republic"));
        countries.add(new Country("TD", "Chad"));
        countries.add(new Country("CL", "Chile"));
        countries.add(new Country("CN", "China"));
        countries.add(new Country("CX", "Christmas Island"));
        countries.add(new Country("CC", "Cocos (Keeling) Islands"));
        countries.add(new Country("CO", "Colombia"));
        countries.add(new Country("KM", "Comoros"));
        countries.add(new Country("CG", "Congo"));
        countries.add(new Country("CK", "Cook Islands"));
        countries.add(new Country("CR", "Costa Rica"));
        countries.add(new Country("HR", "Croatia (Hrvatska)"));
        countries.add(new Country("CU", "Cuba"));
        countries.add(new Country("CY", "Cyprus"));
        countries.add(new Country("CZ", "Czech Republic"));
        countries.add(new Country("DK", "Denmark"));
        countries.add(new Country("DJ", "Djibouti"));
        countries.add(new Country("DM", "Dominica"));
        countries.add(new Country("DO", "Dominican Republic"));
        countries.add(new Country("TP", "East Timor"));
        countries.add(new Country("EC", "Ecuador"));
        countries.add(new Country("EG", "Egypt"));
        countries.add(new Country("SV", "El Salvador"));
        countries.add(new Country("GQ", "Equatorial Guinea"));
        countries.add(new Country("ER", "Eritrea"));
        countries.add(new Country("EE", "Estonia"));
        countries.add(new Country("ET", "Ethiopia"));
        countries.add(new Country("FK", "Falkland Islands (Malvinas)"));
        countries.add(new Country("FO", "Faroe Islands"));
        countries.add(new Country("FJ", "Fiji"));
        countries.add(new Country("FI", "Finland"));
        countries.add(new Country("FR", "France"));
        countries.add(new Country("FX", "France, Metropolitan"));
        countries.add(new Country("GF", "French Guiana"));
        countries.add(new Country("PF", "French Polynesia"));
        countries.add(new Country("TF", "French Southern Territories"));
        countries.add(new Country("GA", "Gabon"));
        countries.add(new Country("GM", "Gambia"));
        countries.add(new Country("GE", "Georgia"));
        countries.add(new Country("DE", "Germany"));
        countries.add(new Country("GH", "Ghana"));
        countries.add(new Country("GI", "Gibraltar"));
        countries.add(new Country("GK", "Guernsey"));
        countries.add(new Country("GR", "Greece"));
        countries.add(new Country("GL", "Greenland"));
        countries.add(new Country("GD", "Grenada"));
        countries.add(new Country("GP", "Guadeloupe"));
        countries.add(new Country("GU", "Guam"));
        countries.add(new Country("GT", "Guatemala"));
        countries.add(new Country("GN", "Guinea"));
        countries.add(new Country("GW", "Guinea-Bissau"));
        countries.add(new Country("GY", "Guyana"));
        countries.add(new Country("HT", "Haiti"));
        countries.add(new Country("HM", "Heard and Mc Donald Islands"));
        countries.add(new Country("HN", "Honduras"));
        countries.add(new Country("HK", "Hong Kong"));
        countries.add(new Country("HU", "Hungary"));
        countries.add(new Country("IS", "Iceland"));
        countries.add(new Country("IN", "India"));
        countries.add(new Country("IM", "Isle of Man"));
        countries.add(new Country("ID", "Indonesia"));
        countries.add(new Country("IR", "Iran (Islamic Republic of)"));
        countries.add(new Country("IQ", "Iraq"));
        countries.add(new Country("IE", "Ireland"));
        countries.add(new Country("IL", "Israel"));
        countries.add(new Country("IT", "Italy"));
        countries.add(new Country("CI", "Ivory Coast"));
        countries.add(new Country("JE", "Jersey"));
        countries.add(new Country("JM", "Jamaica"));
        countries.add(new Country("JP", "Japan"));
        countries.add(new Country("JO", "Jordan"));
        countries.add(new Country("KZ", "Kazakhstan"));
        countries.add(new Country("KE", "Kenya"));
        countries.add(new Country("KI", "Kiribati"));
        countries.add(new Country("KP", "Korea, Democratic People''s Republic of'"));
        countries.add(new Country("KR", "Korea, Republic of"));
        countries.add(new Country("XK", "Kosovo"));
        countries.add(new Country("KW", "Kuwait"));
        
        try{
            countryRepository.deleteAll();
            countryRepository.save(countries);
        } catch(Exception e){
            logger.info(e.getMessage());
        }
        
    }
}
