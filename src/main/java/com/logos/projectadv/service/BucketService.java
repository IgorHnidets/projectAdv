package com.logos.projectadv.service;

import com.logos.projectadv.models.Item;

import java.util.List;

public interface BucketService {
    void create(int id);
    List<Item> getAllItems(int bucketId);
    void insertProduct(int bucketId, int productId);
    void removeProduct(int bucketId, int productId);
}
