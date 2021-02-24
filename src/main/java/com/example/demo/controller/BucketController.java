package com.example.demo.controller;

import com.example.demo.dto.AddPositionToBucketDTO;
import com.example.demo.dto.BucketDTO;
import com.example.demo.dto.converter.BucketEntityDTOConverter;
import com.example.demo.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final BucketEntityDTOConverter bucketEntityDTOConverter;

    @Autowired
    public BucketController(BucketService bucketService, BucketEntityDTOConverter bucketEntityDTOConverter){
        this.bucketService = bucketService;
        this.bucketEntityDTOConverter = bucketEntityDTOConverter;
    }

    @GetMapping
    public List<BucketDTO> findAll(){

        return bucketService.findAll().stream().map(bucketEntityDTOConverter::convertEntity).collect(Collectors.toList());
    }

    @GetMapping("{bucketId}")
    public BucketDTO getBucketById(@PathVariable("bucketId") Long bucketId){

        return bucketEntityDTOConverter.convertEntity(bucketService.findById(bucketId));
    }

    @PostMapping("{customerId}")
    public void addBucket(@PathVariable("customerId") Long customerId){
        System.out.println("customerId: "+customerId );
        bucketService.addBucket(customerId);
    }

    @DeleteMapping("{bucketId}")
    public void deleteBucket(@PathVariable("bucketId") Long bucketId){
        bucketService.deletebucket(bucketId);
    }



    @DeleteMapping(path = "{bucketId}/positions/{productId}")
    public void deleteBucketPositionById(@PathVariable("bucketId") Long bucketId,
                                         @PathVariable("productId") Long productId){
        bucketService.deleteBucketPosition(bucketId,productId);
    }


    @PostMapping(path = "{bucketId}/positions/{productId}")
    public void addBuckerPosition(@PathVariable("bucketId") Long bucketId,
                                  @PathVariable("productId") Long productId,
                                  @Valid @RequestBody AddPositionToBucketDTO addPositionToBucketDTO){

        bucketService.addBucketPosition(bucketId,productId,addPositionToBucketDTO.getQuantity());
    }



}
