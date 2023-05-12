package com.pr32.visualSearch.repository;

import com.pr32.visualSearch.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {
    @Query("{labels: ?0}")
    List<Image> findByLabelsContainingIgnoreCase(String label);
}
