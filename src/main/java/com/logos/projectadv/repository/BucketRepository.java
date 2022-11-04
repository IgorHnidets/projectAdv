package com.logos.projectadv.repository;

import com.logos.projectadv.models.Bucket;
import com.logos.projectadv.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket,Integer> {

//    @Query(value = "SELECT p.item_id,p.name,p.description,p.salary,p.image FROM items_bucket bp " +
//            "INNER JOIN item p on bp.item_id = p.item_id WHERE bp.bucket_id = ?1"
//    )
//    List<Item> getAllItems(@Param("bucket_id") int id);


    Bucket findByBucketId(int bucketId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO items_bucket values (?1,?2)",
    nativeQuery = true)
    void saveIteminBuck(@Param("bucketId") int bucket_Id,
                        @Param("itemId") int item_Id);

    @Transactional
    @Modifying
    @Query(value = "delete from items_bucket b where b.bucket_Id=?1 and b.item_Id=?2",
    nativeQuery = true)
    void removeItemFromBucket(@Param("bucketId") int bucket_id,
                              @Param("itemId") int item_id);
}
