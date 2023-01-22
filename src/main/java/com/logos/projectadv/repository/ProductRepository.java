package com.logos.projectadv.repository;

import com.logos.projectadv.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Item,Integer> {


    @Modifying
    @Transactional
    @Query(value = "delete from items_bucket b where b.item_Id=?1",
    nativeQuery = true)
    void deleteFromBucket(@Param("itemId") int item_id);

    List<Item> findByUserId(int userId);

    List<Item> findByCategory(String category);

    @Modifying
    @Transactional
    @Query(value = "insert into items_users values (?1,?2)",
            nativeQuery = true)
    void addResume(@Param("id") int userId,
                   @Param("item_id") int adminId);

    @Modifying
    @Transactional
    @Query(value = "delete from items_users iu where iu.id = ?1 and iu.item_id=?2",
            nativeQuery = true)
    void delResume(@Param("id") int userId,
                   @Param("item_id") int itemId);

    @Modifying
    @Transactional
    @Query(value = "delete from items_users iu where iu.item_id=?1",
    nativeQuery = true)
    void delItemFromResume(@Param("item_id") int itemId);


}
