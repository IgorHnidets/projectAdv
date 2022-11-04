package com.logos.projectadv.repository;

import com.logos.projectadv.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Item,Integer> {


    @Modifying
    @Transactional
    @Query(value = "delete from items_bucket b where b.item_Id=?1",
    nativeQuery = true)
    void deleteFromBucket(@Param("itemId") int item_id);
}
