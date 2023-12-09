package com.example.batch.repoistory;

import com.example.batch.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(nativeQuery = true, value="SELECT * FROM item i " +
            "JOIN permissions p ON i.permission_group_id = p.group_id " +
            "WHERE i.name = :fileName AND p.user_email = :userEmail AND i.type = 'FILE'")
    Item viewFileMetadata(@Param("fileName") String fileName, @Param("userEmail") String userEmail);
}