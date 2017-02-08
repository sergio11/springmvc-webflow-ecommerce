package persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableRepository<T> {
	
	Page<T> search(String query, Pageable pageable);
}
