package com.example.taskManager.specification;

import com.example.taskManager.dto.ListTaskFilterDTO;
import com.example.taskManager.models.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> build(ListTaskFilterDTO filters) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filters.title() != null && !filters.title().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("title"), filters.title()));
            }
            if(filters.description() != null && !filters.description().isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + filters.description() + "%"));
            }
            if(filters.completed() != null){
                predicates.add(criteriaBuilder.equal(root.get("completed"), filters.completed()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
