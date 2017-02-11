package persistence.repositories;


import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import persistence.models.Product;
import persistence.models.Product_;
import web.models.search.ProductSortEnum;

@Transactional(readOnly = true)
public class ProductRepositoryImpl extends AbstractSearchableJpaRepository<Product> {

    private Sort getQuerySort(QueryBuilder builder, ProductSortEnum productSort) {

        SortFieldContext sortFieldContext = null;

        switch (productSort) {
            case NAME_ASC:
                sortFieldContext = builder.sort().byField(Product_.name.getName()).asc();
                break;
            case NAME_DESC:
                sortFieldContext = builder.sort().byField(Product_.name.getName()).desc();
                break;
            case PRICE_ASC:
                sortFieldContext = builder.sort().byField(Product_.price.getName()).asc();
                break;
            case PRICE_DESC:
                sortFieldContext = builder.sort().byField(Product_.price.getName()).desc();
                break;
            default:
                sortFieldContext = builder.sort().byField(Product_.createAt.getName());
        }

        return sortFieldContext.createSort();

    }

    @Override
    public Page<Product> search(String query, Pageable pageable) {
        
        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
       
        QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(Product.class).get();

        Query lucene = builder.keyword()
                .onFields("name", "description", "shortDescription")
                .matching(query)
                .createQuery();

        FullTextQuery  q =  fullTextEntityManager.createFullTextQuery(lucene, Product.class);
        
        q.setProjection(FullTextQuery.THIS, FullTextQuery.SCORE);

        long total = q.getResultSize();

        // set pageable
        q.setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Product> results = q.getResultList();

        return new PageImpl<Product>(results, pageable, total);
    }

}
