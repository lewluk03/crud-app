package com.example.demo.repository;

import com.example.demo.entity.BucketPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BucketPositionRepository  extends JpaRepository<BucketPosition, Long> {

    List<BucketPosition> findAll();

    Optional<BucketPosition> findById(Long bucketPosId);
}
