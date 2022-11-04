package com.logos.projectadv.service.Impl;

import com.logos.projectadv.models.Bucket;
import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.BucketRepository;
import com.logos.projectadv.service.BucketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;

    @Override
    public void create(int id) {
        Bucket bucket = new Bucket(id,new Timestamp(System.currentTimeMillis()),new User(id));
        bucketRepository.save(bucket);
    }

    @Override
    public List<Item> getAllItems(int bucketId) {
        Bucket byBucketId = bucketRepository.findByBucketId(bucketId);
        List<Item> items = byBucketId.getItems();
        return items;
    }

    @Override
    @Transactional
    public void insertProduct(int bucketId, int itemId) {
        bucketRepository.saveIteminBuck(bucketId,itemId);
    }

    @Override
    public void removeProduct(int bucketId, int itemId) {
        bucketRepository.removeItemFromBucket(bucketId,itemId);
    }
}
