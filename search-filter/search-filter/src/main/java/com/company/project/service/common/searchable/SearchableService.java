package com.company.project.service.common.searchable;

import com.company.project.dao.model.AbstractEntity;
import com.company.project.dao.repository.AbstractRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.project.service.common.searchable.SearchCriteria.Direction.ASCENDING;

public class SearchableService<T extends AbstractEntity> {
    protected final AbstractRepository<T> repository;

    public SearchableService(AbstractRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> findPageBy(SearchCriteria searchCriteria) {
        int page = searchCriteria.getPage();
        int size = searchCriteria.getSize();
        String orderBy = searchCriteria.getOrderBy();
        Sort sort = searchCriteria.getSortDirection() == ASCENDING ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();


        if (searchCriteria.getSearchFields().isEmpty()) {
            return repository.findAll(PageRequest.of(page, size, sort));
        } else {
            return repository.findAll(createSpecificationFor(searchCriteria.getSearchFields()), PageRequest.of(page, size,sort));
        }
    }

    public List<T> findAll(List<SearchField> searchFields) {
        return repository.findAll(createSpecificationFor(searchFields));
    }


    public long count(List<SearchField> searchFields) {
        return repository.count(createSpecificationFor(searchFields));
    }

    private Specification<T> createSpecificationFor(List<SearchField> searchFields) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (SearchField searchField : searchFields) {
                String fieldName = searchField.getName();
                List<String> fields = Arrays.stream(fieldName.split("\\.")).toList();
                Path<?> path = root;

                for (String field : fields) {
                    path = path.get(field);
                }

                if (searchField.getSearchType() == SearchField.SearchType.LIKE) {
                    Expression<String> expression = builder.lower(path.as(String.class));
                    String pattern = "%" + searchField.getValueString().toLowerCase() + "%";
                    predicates.add(builder.like(expression, pattern));
                } else if (searchField.getSearchType() == SearchField.SearchType.EQ_BOOLEAN) {
                    predicates.add(builder.equal(path.as(Boolean.class), searchField.getValueBoolean()));
                } else if (searchField.getSearchType() == SearchField.SearchType.EQ_INT) {
                    predicates.add(builder.equal(path.as(Integer.class), searchField.getValueInt()));
                } else if (searchField.getSearchType() == SearchField.SearchType.EQ_STRING) {
                    predicates.add(builder.equal(builder.lower(path.as(String.class)), searchField.getValueString().toLowerCase()));
                }
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
