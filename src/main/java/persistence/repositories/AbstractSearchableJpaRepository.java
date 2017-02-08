package persistence.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

public abstract class AbstractSearchableJpaRepository<T> extends AbstractDomainClassAwareRepository<T> implements SearchableRepository<T> {
	
	@PersistenceContext 
	protected EntityManager entityManager;
    
    protected FullTextEntityManager getFullTextEntityManager()
    {
       return Search.getFullTextEntityManager(entityManager);

    }
}
