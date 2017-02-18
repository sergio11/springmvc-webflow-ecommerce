package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.TasteBoolPreference;

/**
 * @author sergio
 */
public interface TasteBoolPreferencesRepository 
        extends JpaRepository<TasteBoolPreference, TasteBoolPreference.TastePreferencesId> {}
