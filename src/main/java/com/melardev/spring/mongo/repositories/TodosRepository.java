package com.melardev.spring.mongo.repositories;

import com.melardev.spring.mongo.entities.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TodosRepository extends MongoRepository<Todo, String> {

    // this will not work, Spring data would still treat this
    // method as a query method and the name is not meaningful at all
    // @Query(fields = "{description: 0}", exists = true)

    // Get all documents, all fields except description
    // @Query(fields = "{description: 0}", value = "{id: {$exists:true}}")
    // Get all documents, specified fields only. Id:1 is optional, if skipped then it will be 1 anyways
    @Query(fields = "{id: 1, title: 1, completed:1, createdAt: 1, updatedAt:1}", value = "{id: {$exists:true}}")
    List<Todo> findAllHqlSummary();

    // This is treated as a query method!!! even using @Query, because we have only set fields arg, and not value
    @Query(fields = "{description:0}")
    List<Todo> findByCompletedFalse();

    @Query(fields = "{description:0}")
    Page<Todo> findByCompletedFalse(Pageable pageable);

    // This is not a query method, why? notice the value arg is set.
    @Query(fields = "{description:0}", value = "{completed: false}")
    List<Todo> findByCompletedFalseHql();

    // This is a Spring Data query method
    @Query(fields = "{description:0}")
    List<Todo> findByCompletedIsTrue();

    @Query(fields = "{description:0}")
    Page<Todo> findByCompletedIsTrue(Pageable pageable);

    @Query(fields = "{description:0}", value = "{completed: true}")
    List<Todo> findByCompletedIsTrueHql();


    List<Todo> findByCompletedTrue();

    List<Todo> findByCompletedIsFalse();

    List<Todo> findByCompleted(boolean done);

    List<Todo> findByTitleContains(String title);

    List<Todo> findByDescriptionContains(String description);

    Todo findByTitleAndDescription(String title, String description);


    List<Todo> findByCreatedAtAfter(LocalDateTime date);

    List<Todo> findByCreatedAtBefore(LocalDateTime date);
}