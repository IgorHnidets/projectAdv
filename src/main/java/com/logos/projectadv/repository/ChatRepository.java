package com.logos.projectadv.repository;

import com.logos.projectadv.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query(value = "select c from Chat c where c.user.id = ?1 and c.getterId = ?2 or c.user.id = ?2 and c.getterId = ?1")
    List<Chat> getMessage(@Param("userId") int userId,
                          @Param("getterId") int getterId);



}
