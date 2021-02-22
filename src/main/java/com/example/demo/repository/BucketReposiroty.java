package com.example.demo.repository;

import com.example.demo.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BucketReposiroty extends JpaRepository<Bucket, Long> {

    Optional<Bucket> findById(Long bucketId);

    List<Bucket> findAll ();
}
