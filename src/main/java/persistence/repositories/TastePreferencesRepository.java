package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.TastePreferences;

/**
 * @author sergio
 */
public interface TastePreferencesRepository extends JpaRepository<TastePreferences, TastePreferences.TastePreferencesId> {}
